package com.sise.oj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.annotation.rest.AnonymousGetMapping;
import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.Contest;
import com.sise.oj.domain.Judge;
import com.sise.oj.domain.dto.PidListDto;
import com.sise.oj.domain.param.ProblemQueryParam;
import com.sise.oj.domain.vo.ProblemInfoVo;
import com.sise.oj.domain.vo.ProblemVo;
import com.sise.oj.enums.Constants;
import com.sise.oj.service.ContestService;
import com.sise.oj.service.JudgeService;
import com.sise.oj.service.ProblemService;
import com.sise.oj.util.SecurityUtils;
import com.sise.oj.util.ValidationUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cijee
 * @version 1.0
 */
@RestController
@Api(tags = "题目接口")
@RequiredArgsConstructor
@RequestMapping("/problems")
public class ProblemController {

    private final ProblemService problemService;
    private final ContestService contestService;
    private final JudgeService judgeService;

    @ApiOperation("分页查询题目")
    @AnonymousGetMapping
    public ResultJson<Page<ProblemVo>> list(ProblemQueryParam param, Page<ProblemVo> page) {
        return ResultJson.success(problemService.list(param, page));
    }

    @ApiOperation("查询题目详细信息")
    @AnonymousGetMapping("/{id}")
    public ResultJson<ProblemInfoVo> getInfo(@PathVariable Long id) {
        return ResultJson.success(problemService.getInfo(id));
    }

    @ApiOperation("获取对应该题目列表中用户各个题目的解答情况")
    @PostMapping("/get-user-status")
    public ResultJson<Object> getUserProblemStatus(@Validated @RequestBody PidListDto pidListDto) {
        // 获取当前的用户id
        Long uid = SecurityUtils.getCurrentUserId();
        Map<String, Object> result = new HashMap<>();
        // 先查询该用户的题目是否已经通过了
        QueryWrapper<Judge> wrapper = new QueryWrapper<>();
        wrapper.select("distinct pid,status,submit_time,score").in("pid", pidListDto.getIds())
                .eq(pidListDto.getContestProblem(), "cid", pidListDto.getCid())
                .eq("uid", uid)
                .orderByDesc("submit_time");
        List<Judge> judgeList = judgeService.list(wrapper);
        boolean isACMContest = true;
        if (pidListDto.getContestProblem()) {
            Contest contest = contestService.getById(pidListDto.getCid());
            ValidationUtils.isNull(contest, "比赛", "ID", pidListDto.getCid());
            isACMContest = Constants.Contest.TYPE_GENERAL.getCode().equals(contest.getType());
        }
        for (Judge judge : judgeList) {
            Map<String, Object> temp = new HashMap<>();
            // 如果该题目已通过，则强制写为通过
            if (pidListDto.getContestProblem()) {
                if (isACMContest) {
                    // 如果该题目通过，则为AC
                    if (Constants.Judge.AC.getStatus().equals(judge.getStatus())) {
                        temp.put("status", Constants.Judge.AC.getStatus());
                        temp.put("score", null);
                        result.put(judge.getPid().toString(), temp);
                    } else if (!result.containsKey(judge.getPid().toString())) { // 还未写入，使用最新的提交
                        temp.put("status", judge.getStatus());
                        temp.put("score", null);
                        result.put(judge.getPid().toString(), temp);
                    }
                } else { // rating赛
                    temp.put("status", judge.getStatus());
                    temp.put("score", judge.getScore());
                    result.put(judge.getPid().toString(), temp);
                }
            } else {
                if (Constants.Judge.AC.getStatus().equals(judge.getStatus())) {
                    result.put(judge.getPid().toString(), Constants.Judge.AC.getStatus());
                } else if (!result.containsKey(judge.getPid().toString())) {
                    result.put(judge.getPid().toString(), judge.getStatus());
                }
            }
        }
        // 再次检查，应该可能从未提交过该题，则状态写为-10
        for (Long pid : pidListDto.getIds()) {
            // 如果是比赛的题目列表状态
            if (pidListDto.getContestProblem()) {
                if (!result.containsKey(pid.toString())) {
                    HashMap<String, Object> temp = new HashMap<>();
                    temp.put("score", null);
                    temp.put("status", -1);
                    result.put(pid.toString(), temp);
                }
            } else {
                if (!result.containsKey(pid.toString())) {
                    result.put(pid.toString(), -1);
                }
            }
        }
        return ResultJson.success(result);
    }
}
