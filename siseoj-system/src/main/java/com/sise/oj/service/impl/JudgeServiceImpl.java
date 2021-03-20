package com.sise.oj.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseServiceImpl;
import com.sise.oj.domain.*;
import com.sise.oj.domain.dto.JudgeDto;
import com.sise.oj.domain.param.SubmissionQueryParam;
import com.sise.oj.enums.Constants;
import com.sise.oj.exception.BusinessException;
import com.sise.oj.exception.DataNotFoundException;
import com.sise.oj.judge.client.OnlineJudgeHttpClient;
import com.sise.oj.judge.entity.CaseResult;
import com.sise.oj.judge.entity.JudgeResult;
import com.sise.oj.judge.entity.JudgeSubmitParam;
import com.sise.oj.mapper.JudgeMapper;
import com.sise.oj.service.*;
import com.sise.oj.util.SecurityUtils;
import com.sise.oj.util.StringUtils;
import com.sise.oj.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * JudgeStatusServiceImpl
 *
 * @author Cijee
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JudgeServiceImpl extends BaseServiceImpl<JudgeMapper, com.sise.oj.domain.Judge> implements JudgeService {

    private final JudgeMapper judgeMapper;
    private final JudgeCaseService judgeCaseService;
    private final ProblemCountService problemCountService;
    private final ProblemService problemService;
    private final ContestProblemService contestProblemService;
    private final ContestRecordService contestRecordService;
    private final ContestService contestService;
    private final UserRecordService userRecordService;
    private final UserAcceptService userAcceptService;
    private final OnlineJudgeHttpClient onlineJudgeHttpClient;
    private final ExecutorService pool;

    @Override
    public Page<Judge> list(SubmissionQueryParam param, Page<Judge> page) {
        LambdaUpdateWrapper<Judge> wrapper = Wrappers.lambdaUpdate(Judge.class);
        if (Objects.nonNull(param.getPid())) {
            wrapper.eq(Judge::getPid, param.getPid());
        }
        if (Objects.nonNull(param.getStatus())) {
            wrapper.eq(Judge::getStatus, param.getStatus());
        }
        if (Objects.nonNull(param.getLanguage())) {
            wrapper.eq(Judge::getLanguage, param.getLanguage());
        }
        if (StringUtils.isNotBlank(param.getUsername())) {
            wrapper.like(Judge::getUsername, param.getUsername());
        }
        wrapper.orderByDesc(Judge::getId);
        return judgeMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public Judge submitProblemJudge(JudgeDto judgeDto) {
        Date currentTime = new Date();
        // 先获取当前的用户数据
        Long uid = SecurityUtils.getCurrentUserId();
        String username = SecurityUtils.getCurrentUsername();
        // 将提交先写入数据库，准备调用判题服务器
        Judge judge = new Judge();
        judge.setSubmitTime(currentTime);
        judge.setPid(judgeDto.getPid()); // 题号
        judge.setUid(uid); // 用户ID
        judge.setCid(judgeDto.getCid()); // 比赛号
        judge.setUsername(username);
        judge.setLanguage(judgeDto.getLanguage());
        judge.setCode(judgeDto.getCode());
        judge.setCodeLength(judgeDto.getCode().length());
        judge.setShare(true); // 默认设置代码为所有人(AC了该题目的用户)可见

        Problem problem;
        boolean contestRecordResult = true, userRecordResult = true;
        int judgeResult;

        // 判断是否为比赛判题(比赛id不等于0，则说明为比赛提交，需要查询对应的contest的主键)
        if (judgeDto.getCid() != 0) { // 比赛提交
            // 首先判断一下比赛的状态是否是正在进行，结束状态都不能提交，比赛前比赛管理员可以提交
            Contest contest = contestService.getById(judgeDto.getCid());
            if (contest == null) {
                throw new DataNotFoundException("对不起，该比赛不存在");
            }
            if (contest.getStatus().intValue() == Constants.Contest.STATUS_ENDED.getCode()) {
                throw new BusinessException("比赛已结束，不可再提交！");
            }
            // 不是超级管理员或者该比赛的创建者,不可提交题目
            boolean root = SecurityUtils.isSuperAdmin();
            if (!root && !contest.getUid().equals(uid)) {
                if (contest.getStatus().intValue() == Constants.Contest.STATUS_SCHEDULED.getCode()) {
                    throw new BusinessException("比赛未开始，不可提交");
                }
                // 普通用户需要检查是否有权限提交
                contestService.checkJudgeAuth(contest, uid, judgeDto.getContestPassword());
            }

            // 比赛的话，前端提交的pid是比赛显示的比赛题号，不是真正的题目id
            ContestProblem contestProblem = contestProblemService.getOne(Wrappers.lambdaQuery(ContestProblem.class)
                    .eq(ContestProblem::getCid, judgeDto.getCid())
                    .eq(ContestProblem::getDisplayId, judgeDto.getPid()));
            ValidationUtils.isNull(contestProblem, "比赛题目", "ID", judgeDto.getPid());
            // 设置比赛ID
            judge.setCid(contestProblem.getCid());
            // 设置比赛题目ID
            judge.setContestPid(contestProblem.getId());
            problem = problemService.getById(contestProblem.getPid());
            if (problem == null) {
                throw new DataNotFoundException("该题目不存在");
            }
            judge.setPid(problem.getPid());

            // 将新提交插入数据库
            judgeResult = judgeMapper.insert(judge);

            // 比赛正在运行
            if (contest.getStatus().intValue() == Constants.Contest.STATUS_RUNNING.getCode()) {
                // 先查询是否为首次可能AC提交
                boolean firstACAttempt = contestRecordService.count(Wrappers.lambdaQuery(ContestRecord.class)
                        .eq(ContestRecord::getCid, judgeDto.getCid())
                        .eq(ContestRecord::getContestPid, judgeDto.getPid())
                        .eq(ContestRecord::getStatus, Constants.Judge.AC.getStatus())) == 0;
                // 同时初始化写入contest_record表
                ContestRecord contestRecord = new ContestRecord();
                // 设置比赛显示的题目号
                contestRecord.setDisplayId(Math.toIntExact(judgeDto.getPid()));
                contestRecord.setContestPid(contestProblem.getId());
                contestRecord.setJudgeId(judge.getId());
                contestRecord.setPid(judge.getPid());
                contestRecord.setUsername(username);
                contestRecord.setUid(uid);
                contestRecord.setCid(judge.getCid());
                contestRecord.setSubmitTime(judge.getSubmitTime());
                // 设置比赛开始时间到提交时间之间的秒数
                contestRecord.setTime(DateUtil.between(contest.getStartTime(), judge.getSubmitTime(), DateUnit.SECOND));
                // 设置是否是一血
                contestRecord.setFirstBlood(firstACAttempt);
                contestRecordResult = contestRecordService.save(contestRecord);
            }
        } else { // 如果不是比赛提交
            // 查看是否存在该题目
            problem = problemService.getOne(Wrappers.lambdaQuery(Problem.class)
                    .eq(Problem::getPid, judgeDto.getPid()));
            ValidationUtils.isNull(problem, "题目", "ID", judgeDto.getPid());
            judge.setContestPid(0L);
            judge.setPid(problem.getPid());

            // 更新下user_record表
            LambdaUpdateWrapper<UserRecord> wrapper = Wrappers.lambdaUpdate(UserRecord.class);
            wrapper.setSql("submissions=submissions+1").eq(UserRecord::getUid, judge.getUid());
            userRecordResult = userRecordService.update(wrapper);
            // 插入评测表
            judgeResult = judgeMapper.insert(judge);
        }

        if (judgeResult != 1 || !contestRecordResult || !userRecordResult) {
            throw new BusinessException("代码提交失败");
        }

        // 提交到判题服务器
        try {
            onlineJudgeHttpClient.submit(new JudgeSubmitParam("submit",
                    problem.getPid(),
                    judge.getId(),
                    problem.getTimeLimit(),
                    problem.getMemoryLimit(),
                    judge.getCode(),
                    judge.getLanguage()
            ));
        } catch (Exception e) {
            throw new BusinessException("提交到评测机出现异常" + e.getMessage());
        }
        // 成功提交，修改题目状态
        judgeMapper.updateStatusById(Constants.Judge.PENDING.getStatus(), judge.getId());
        return judge;
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void getJudgeStatus(Long submitId) throws Exception {
        String status = "AC";
        // 获取数据库中的评测表数据
        Judge judge = judgeMapper.selectById(submitId);
        ValidationUtils.isNull(judge, "提交", "ID", judge.getPid());
        if (Constants.Judge.PENDING.getStatus().equals(judge.getStatus()) ||
                Constants.Judge.JUDGING.getStatus().equals(judge.getStatus())) {
            // 定时请求判题机获取判题结果
            boolean isLoop = true; //结束请求标致
            int requestCount = 60; // 请求次数，2分钟
            int timeStep = 2; // 请求时间间隔2s
            while (isLoop && requestCount > 0) {
                JudgeResult judgeResult = onlineJudgeHttpClient.result(submitId);
                // 如果是待评测
                if ("pending".equals(judgeResult.getType())) {
                    // 不做操作
                    judge.setStatus(Constants.Judge.PENDING.getStatus());
                }
                // 如果是评测中
                else if ("judging".equals(judgeResult.getType())) {
                    // 不做操作
                    judge.setStatus(Constants.Judge.JUDGING.getStatus());
                }
                // 编译错误
                else if ("error".equals(judgeResult.getType())) {
                    status = judgeResult.getType();
                    judge.setStatus(Constants.Judge.CE.getStatus());
                    judge.setError_message(judgeResult.getMsg());
                    judgeMapper.updateById(judge);
                    // 退出循环
                    isLoop = false;
                }
                // 编译成功，但是不一定AC
                else {
                    List<CaseResult> caseResults = judgeResult.getCaseResults();
                    status = "AC";
                    int maxTime = 0, maxMemory = 0;
                    // 遍历所有的测试点
                    for (CaseResult caseResult : caseResults) {
                        // 设置所有测试点中，消耗内存最大的
                        maxTime = Math.max(maxTime, caseResult.getTime());
                        maxMemory = Math.max(maxMemory, caseResult.getMemory());
                        // 如果有测试点未通过
                        if (!status.equals(caseResult.getResult())) {
                            status = caseResult.getResult();
                            break;
                        }
                    }
                    // 设置评测状态
                    judge.setStatus(Constants.Judge.valueOf(status).getStatus());
                    judge.setTime(maxTime);
                    judge.setMemory(maxMemory);
                    judgeMapper.updateById(judge);
                    // 退出循环
                    isLoop = false;
                }
                requestCount--;
                // 每次请求间隔2s
                TimeUnit.SECONDS.sleep(timeStep);
            }
        }
        if (judge.getCid() == 0) { // 不是比赛提交
            // 如果AC了该题目，更新user_accept表
            if ("AC".equals(status)) {
                userAcceptService.save(new UserAccept()
                        .setPid(judge.getPid())
                        .setUid(judge.getUid())
                        .setJudgeId(judge.getId())
                );
            }
        } else {
            Contest contest = contestService.getById(judge.getCid());
            if (contest == null) {
                log.error("判题服务出错----------->{}", "该比赛不存在");
            }
            // 每个提交都得记录到contest_record里面,同时需要判断是否为比赛时的提交
            if (contest.getStatus().intValue() == Constants.Contest.STATUS_RUNNING.getCode()) {
                // TODO 更新比赛题目提交记录
                //contestRecordService.UpdateContestRecord(uid,score,status,submitId, cid)
            }
        }
        // 更新该题目的统计数据
        Long pid = judge.getPid();
        // 先查看是否有该题目的统计数据
        ProblemCount problemCount = problemCountService.getOne(Wrappers.lambdaQuery(ProblemCount.class)
                .eq(ProblemCount::getPid, pid));
        // 如果没有该题目的统计数据，则表示是第一次提交
        if (problemCount == null) {
            problemCountService.save(new ProblemCount()
                    .setPid(pid).setTotal(1).setAccept("AC".equals(status) ? 1 : 0));
        } else {
            problemCount.setTotal(problemCount.getTotal() + 1);
            problemCount.setAccept(problemCount.getAccept() + ("AC".equals(status) ? 1 : 0));
            problemCountService.updateById(problemCount);
        }
    }
}
