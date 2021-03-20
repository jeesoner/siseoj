package com.sise.oj.controller;

/**
 * @author Cijee
 * @version 1.0
 */

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.annotation.rest.AnonymousGetMapping;
import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.Contest;
import com.sise.oj.domain.param.QueryParam;
import com.sise.oj.domain.vo.ContestProblemVo;
import com.sise.oj.domain.vo.ProblemInfoVo;
import com.sise.oj.service.ContestProblemService;
import com.sise.oj.service.ContestService;
import com.sise.oj.service.ProblemService;
import com.sise.oj.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "比赛接口")
@RequestMapping("/contests")
public class ContestController {

    private final ContestService contestService;
    private final ContestProblemService contestProblemService;
    private final ProblemService problemService;

    @ApiOperation("分页查询比赛")
    //@GetMapping
    @AnonymousGetMapping
    public ResultJson<Page<Contest>> list(Page<Contest> page, QueryParam param) {
        return ResultJson.success(contestService.list(page, param));
    }

    @ApiOperation("获取比赛信息")
    @GetMapping("/{cid}")
    public ResultJson<Contest> getContest(@PathVariable Long cid) {
        return ResultJson.success(contestService.findById(cid));
    }

    /**
     * [公开赛可查询，私有赛[超级管理和创建者可以] ]
     * 获得指定比赛的题目列表
     *
     * @param cid 比赛ID
     * @return ResultJson
     */
    @ApiOperation("获取比赛列表信息")
    @GetMapping("/{cid}/problems")
    public ResultJson<List<ContestProblemVo>> getContestProblem(@PathVariable Long cid) {
        // 获取当前的用户
        Long currentUserId = SecurityUtils.getCurrentUserId();
        boolean isRoot = SecurityUtils.isSuperAdmin();
        // 获取本场比赛
        Contest contest = contestService.findById(cid);
        // 检查是否有权限查看该比赛
        contestService.checkContestAuth(contest, currentUserId, isRoot);
        return ResultJson.success(contestProblemService.getContestProblemList(cid, contest.getStartTime()));
    }

    @ApiOperation("获取指定比赛题目的详细信息")
    @GetMapping("/{cid}/problems/{displayId}")
    public ResultJson<ProblemInfoVo> getContestProblemDetails(@PathVariable Long cid,
                                                              @PathVariable Integer displayId) {
        // 获取当前的用户
        Long currentUserId = SecurityUtils.getCurrentUserId();
        boolean isRoot = SecurityUtils.isSuperAdmin();
        // 获取本场比赛
        Contest contest = contestService.findById(cid);
        // 检查是否有权限查看该比赛
        contestService.checkContestAuth(contest, currentUserId, isRoot);
        // 根据display_id和cid获取题目信息
        return ResultJson.success(contestService.getContestProblemDetails(contest, displayId));
    }
}