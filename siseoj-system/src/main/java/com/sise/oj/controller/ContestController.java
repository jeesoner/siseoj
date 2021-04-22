package com.sise.oj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.annotation.rest.AnonymousGetMapping;
import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.Contest;
import com.sise.oj.domain.ContestRegister;
import com.sise.oj.domain.Judge;
import com.sise.oj.domain.param.ContestRegisterParam;
import com.sise.oj.domain.param.QueryParam;
import com.sise.oj.domain.param.SubmissionQueryParam;
import com.sise.oj.domain.vo.ContestProblemVo;
import com.sise.oj.domain.vo.ContestRankVo;
import com.sise.oj.domain.vo.ProblemInfoVo;
import com.sise.oj.exception.BadRequestException;
import com.sise.oj.service.*;
import com.sise.oj.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    private final ContestRecordService contestRecordService;
    private final ContestRegisterService contestRegisterService;
    private final JudgeService judgeService;

    @ApiOperation("分页查询比赛")
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

    @ApiOperation("获取指定比赛的评测记录")
    @GetMapping("/{cid}/submissions")
    public ResultJson<Page<Judge>> listContestSubmissions(@PathVariable Long cid,
                                                          Page<Judge> page,
                                                          SubmissionQueryParam param) {
        return ResultJson.success(judgeService.getContestSubmissionList(page, cid, param));
    }

    @ApiOperation("获取指定比赛的排行情况")
    @GetMapping("/{cid}/rank")
    public ResultJson<Page<ContestRankVo>> getContestRank(@PathVariable Long cid,
                                                          Page<ContestRankVo> page) {
        // 获取当前的用户
        Long currentUserId = SecurityUtils.getCurrentUserId();
        boolean isRoot = SecurityUtils.isSuperAdmin();
        // 获取本场比赛
        Contest contest = contestService.findById(cid);
        // 检查是否有权限查看该比赛
        contestService.checkContestAuth(contest, currentUserId, isRoot);
        return ResultJson.success(contestRecordService.getContestACMRank(page, cid));
    }

    @ApiOperation("注册比赛")
    @PostMapping("/register")
    public ResultJson<Object> toRegisterContest(@RequestBody ContestRegisterParam param) {
        // 获取当前登录的用户
        Long uid = SecurityUtils.getCurrentUserId();

        Contest contest = contestService.getById(param.getCid());

        // 密码不对
        if (!contest.getPassword().equals(param.getPassword())) {
            throw new BadRequestException("比赛密码错误");
        }

        QueryWrapper<ContestRegister> wrapper = new QueryWrapper<ContestRegister>().eq("cid", param.getCid())
                .eq("uid", uid);
        if (contestRegisterService.getOne(wrapper) != null) {
            throw new BadRequestException("您已注册过该比赛，请勿重复注册！");
        }

        ContestRegister contestRegister = new ContestRegister();
        contestRegister.setCid(param.getCid());
        contestRegister.setUid(uid);
        boolean result = contestRegisterService.saveOrUpdate(contestRegister);

        if (!result) {
            throw new BadRequestException("校验比赛密码失败，请稍后再试");
        }
        return ResultJson.success(null);
    }

    @ApiOperation("获得指定私有比赛的访问权限或保护比赛的提交权限")
    @GetMapping("/access")
    public ResultJson<Object> getContestAccess(@RequestParam(value = "cid") Long cid) {
        // 获取当前登录的用户
        Long uid = SecurityUtils.getCurrentUserId();

        QueryWrapper<ContestRegister> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid", cid).eq("uid", uid);
        ContestRegister contestRegister = contestRegisterService.getOne(queryWrapper);
        HashMap<String, Object> result = new HashMap<>();
        result.put("access", contestRegister != null);
        return ResultJson.success(result);
    }
}