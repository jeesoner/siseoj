package com.sise.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseServiceImpl;
import com.sise.oj.domain.*;
import com.sise.oj.domain.param.QueryParam;
import com.sise.oj.domain.vo.ProblemInfoVo;
import com.sise.oj.enums.Constants;
import com.sise.oj.enums.ResultCode;
import com.sise.oj.exception.BadRequestException;
import com.sise.oj.exception.BusinessException;
import com.sise.oj.exception.DataExistException;
import com.sise.oj.exception.DataNotFoundException;
import com.sise.oj.mapper.ContestMapper;
import com.sise.oj.service.*;
import com.sise.oj.util.StringUtils;
import com.sise.oj.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Cijee
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ContestServiceImpl extends BaseServiceImpl<ContestMapper, Contest> implements ContestService {

    private final ContestMapper contestMapper;
    private final ContestProblemService contestProblemService;
    private final ContestRegisterService contestRegisterService;
    private final ProblemTagService problemTagService;
    private final SourceService sourceService;
    private final ProblemService problemService;
    private final TagService tagService;

    @Override
    public Page<Contest> list(Page<Contest> page, QueryParam param) {
        LambdaQueryWrapper<Contest> wrapper = Wrappers.lambdaQuery(Contest.class);
        if (StringUtils.isNoneBlank(param.getKeyword())) {
            wrapper.like(Contest::getTitle, param.getKeyword());
        }
        if (param.getStatus() != null) {
            wrapper.eq(Contest::getStatus, param.getStatus());
        }
        // 根据比赛开始时间倒序排列，快开始的比赛排列在前面
        wrapper.orderByDesc(Contest::getStartTime);
        return contestMapper.selectPage(page, wrapper);
    }

    @Override
    public void checkContestAuth(Contest contest, Long uid, boolean isRoot) {
        if (contest == null) {
            throw new BusinessException("对不起，该比赛不存在");
        }
        // 如果是比赛管理者或超级管理员
        if (isRoot || contest.getUid().equals(uid)) return ;
        // 判断比赛状态，比赛未开始，不可访问
        if (contest.getStatus().intValue() == Constants.Contest.STATUS_SCHEDULED.getCode()) {
            throw new BadRequestException(ResultCode.FORBIDDEN, "比赛还未开始，您无权访问该比赛！");
        }
        // 比赛正在进行阶段，需要判断该场比赛是否为私有赛，私有赛需要判断该用户是否已注册
        if (contest.getStatus().intValue() == Constants.Contest.STATUS_RUNNING.getCode() &&
                contest.getAuth().intValue() == Constants.Contest.AUTH_PRIVATE.getCode()) {
            QueryWrapper<ContestRegister> registerQueryWrapper = new QueryWrapper<>();
            // 查询比赛注册表中该用户是否注册了比赛
            registerQueryWrapper.eq("cid", contest.getId()).eq("uid", uid);
            ContestRegister register = contestRegisterService.getOne(registerQueryWrapper);
            // 用户未注册私有赛，不可访问
            if (register == null) {
                throw new BusinessException("对不起，该比赛为私有赛，请先注册该比赛！");
            }
        }
    }

    @Override
    public void checkJudgeAuth(Contest contest, Long uid, String password) {
        // 受密码保护的比赛
        if (contest.getAuth().intValue() == Constants.Contest.AUTH_PRIVATE.getCode() ||
                contest.getAuth().intValue() == Constants.Contest.AUTH_PROTECT.getCode()) {
            QueryWrapper<ContestRegister> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("cid", contest.getId()).eq("uid", uid);
            ContestRegister register = contestRegisterService.getOne(queryWrapper, false);
            // 如果还没注册
            if (register == null) {
                // 如果提交密码不为空，且跟当前比赛的密码相等，则进行注册，并且此次提交可以提交,同时注册到数据库
                if (StringUtils.isEmpty(password)) {
                    throw new BusinessException("对不起，提交失败！请您先注册该比赛！");
                } else if (contest.getPassword().equals(password)) {
                    ContestRegister contestRegister = new ContestRegister();
                    contestRegister.setUid(uid);
                    contestRegister.setCid(contest.getId());
                    contestRegisterService.saveOrUpdate(contestRegister);
                } else {
                    throw new BusinessException("对不起，比赛密码错误，提交代码失败！");
                }
            }
        }
    }

    @Override
    public Contest findById(Long id) {
        Contest contest = contestMapper.selectById(id);
        ValidationUtils.isNull(contest, "比赛", "ID", id);
        return contest;
    }

    @Override
    public void insert(Contest resources) {
        contestMapper.insert(resources);
    }

    @Override
    public void update(Contest resources) {
        Contest contest = contestMapper.selectById(resources.getId());
        ValidationUtils.isNull(contest, "比赛", "ID", resources.getId());

        contest.setTitle(resources.getTitle());
        contest.setType(resources.getType());
        contest.setDescription(resources.getDescription());
        contest.setSource(resources.getSource());
        contest.setAuth(resources.getAuth());
        contest.setStartTime(resources.getStartTime());
        contest.setEndTime(resources.getEndTime());
        contest.setDuration(resources.getDuration());
        contest.setSealRank(resources.getSealRank());
        contest.setSealRankTime(resources.getSealRankTime());

        contestMapper.updateById(contest);
    }

    @Override
    public void delete(Long id) {
        Contest contest = contestMapper.selectById(id);
        ValidationUtils.isNull(contest, "比赛", "ID", id);
        // 如果比赛不是未开始的比赛，不能删除
        if (!Constants.Contest.STATUS_SCHEDULED.getCode().equals(contest.getStatus())) {
            throw new BadRequestException("对不起，您不能删除进行中或已结束的比赛！");
        }
        // 先删除比赛题目
        contestProblemService.remove(Wrappers.lambdaQuery(ContestProblem.class)
                .eq(ContestProblem::getCid, id));
        // 删除比赛
        contestMapper.deleteById(id);
    }

    @Override
    public Page<Problem> getProblemList(Page<Problem> page, QueryParam param) {
        // 分页查询
        List<Problem> problemList = contestMapper.getProblemList(page, param.getKeyword(), param.getId());
        return page.setRecords(problemList);
    }

    @Override
    public ProblemInfoVo getContestProblemDetails(Contest contest, Integer displayId) {
        // 根据display_id和cid获取题目信息
        ContestProblem contestProblem = contestProblemService.findByContestIdAndDisplayId(contest.getId(), displayId);
        if (contestProblem == null) {
            throw new DataNotFoundException("该题目不存在ID: " + displayId);
        }
        // 查询题目详情，题目标签
        ProblemInfoVo problemInfoVo = new ProblemInfoVo();
        Problem problem = problemService.getById(contestProblem.getPid());
        if (problem == null) {
            throw new DataNotFoundException("该题目不存在ID: " + contestProblem.getPid());
        }
        problem.setPid(contestProblem.getDisplayId().longValue());
        problem.setTitle(contestProblem.getDisplayTitle());
        problemInfoVo.setProblem(problem);
        // 比赛结束后才可以查看题目标签和题目来源
        if (contest.getStatus().intValue() == Constants.Contest.STATUS_ENDED.getCode()) {
            List<ProblemTag> problemTagList = problemTagService.list(Wrappers.lambdaQuery(ProblemTag.class)
                    .eq(ProblemTag::getPid, problem.getPid()));
            // 获取该题目对应的标签
            if (problemTagList.size() > 0) {
                Set<Long> tagIds = problemTagList.stream().map(ProblemTag::getTid).collect(Collectors.toSet());
                List<Tag> tags = tagService.listByIds(tagIds);
                problemInfoVo.setTags(tags);
            }
            // 查看题目的来源
            problemInfoVo.setSource(sourceService.getById(problem.getSourceId()));
        }
        return problemInfoVo;
    }

    /**
     * 只能修改未开始的比赛题目，已经开始或结束的不能修改比赛题目
     *
     * 1. 因为比赛题目是有顺序的，故而更新操作比较复杂。此时前端的列表是最终我们要显示的顺序。现在的主要问题是，如何
     * 将前端增删或者重排后的题目，以最优的方式更新到数据库中。
     * 2.这里以新题目作为前端的题目，老题目作为后端数据库中的题目。新老题目共同的部分，仅需要修改其display_id，即
     * 改变其排序，新题目比旧题目新多的部分，是要增加到数据库中的，缺失的部分是要删除的。
     * 3.逻辑顺序：先删除，后增加，再修改
     *
     * @param resources List
     * @param cid 比赛ID
     */
    @Override
    public void updateContestProblemList(List<ContestProblem> resources, Long cid) {
        Contest contest = contestMapper.selectById(cid);
        ValidationUtils.isNull(contest, "比赛", "ID", cid);
        // 如果比赛不是未开始的比赛，不能删除
        if (!Constants.Contest.STATUS_SCHEDULED.getCode().equals(contest.getStatus())) {
            throw new BadRequestException("对不起，您不能修改进行中或已结束的比赛题目！");
        }
        // 设置排序的ID和比赛ID
        for (int i = 0; i < resources.size(); i++) {
            ContestProblem contestProblem = resources.get(i);
            contestProblem.setDisplayId(i);
            contestProblem.setCid(cid);
        }
        // 查询数据库里的比赛题目
        List<ContestProblem> contestProblemList = contestProblemService.findByContestId(cid);
        // 数据库原有的比赛题目map集合
        Map<Long, ContestProblem> oldContestProblemMap = contestProblemList.stream()
                .collect(Collectors.toMap(ContestProblem::getId, item -> item));

        List<ContestProblem> addList = new LinkedList<>(); // 待增加的比赛题目集合
        Set<Long> delSet = new HashSet<>(oldContestProblemMap.keySet()); // 待删除的比赛题目ID集合
        List<ContestProblem> editList = new LinkedList<>(); // 待更新的比赛题目集合

        // 前端传来的新的比赛题目ID集合
        Set<Long> newSet = new HashSet<>();

        for (ContestProblem contestProblem : resources) {
            // 新增的题目
            if (contestProblem.getId() == null) {
                if (contestProblemService.findByContestIdAndPid(cid, contestProblem.getPid()) != null) {
                    throw new DataExistException("不允许出现重复的题目[" + contestProblem.getPid() +"]!");
                }
                // 添加到待新增的比赛题目中
                addList.add(contestProblem);
            } else {
                // 添加到前端的ID集合中
                newSet.add(contestProblem.getId());
                // 判断是否需要更新
                ContestProblem oldContestProblem = oldContestProblemMap.get(contestProblem.getId());
                if (oldContestProblem != null) { // Map拿得到数据，说明前端和后端有相同的比赛题目
                    // 加入待更新集合
                    editList.add(contestProblem);
                }
            }
        }
        // 计算待删除的比赛题目
        delSet.removeAll(newSet);
        // 1.删除旧的比赛题目
        if (delSet.size() > 0) {
            contestProblemService.removeByIds(delSet);
        }
        // 2.增加新的比赛题目
        if (addList.size() > 0) {
            contestProblemService.saveBatch(addList);
        }
        // 3.更新比赛题目的display_id
        if (editList.size() > 0) {
            contestProblemService.updateBatchById(editList);
        }
    }
}
