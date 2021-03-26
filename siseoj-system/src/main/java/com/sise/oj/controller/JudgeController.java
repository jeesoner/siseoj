package com.sise.oj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.annotation.rest.AnonymousGetMapping;
import com.sise.oj.annotation.rest.AnonymousPostMapping;
import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.Judge;
import com.sise.oj.domain.Problem;
import com.sise.oj.domain.dto.JudgeDto;
import com.sise.oj.domain.param.SubmissionQueryParam;
import com.sise.oj.domain.param.SubmitParam;
import com.sise.oj.exception.BadRequestException;
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
@RequiredArgsConstructor
@Api(tags = "判题接口")
@RequestMapping
public class JudgeController {

    private final JudgeService judgeService;
    private final ProblemService problemService;

    @ApiOperation("获取所有的提交记录")
    @AnonymousGetMapping("/submissions")
    public ResultJson<Page<Judge>> listSubmission(SubmissionQueryParam param, Page<Judge> page) {
        return ResultJson.success(judgeService.list(param, page));
    }

    @ApiOperation("获取单个提交记录的详情")
    @GetMapping("/submission")
    public ResultJson<Object> getSubmission(@RequestParam("submitId") Long submitId) {
        Judge judge = judgeService.getById(submitId);
        ValidationUtils.isNull(judge, "提交", "ID", submitId);
        Long uid = SecurityUtils.getCurrentUserId();
        boolean root = SecurityUtils.isSuperAdmin();
        if (!root && !judge.getUid().equals(uid)) {
            throw new BadRequestException("对不起！该提交并未分享，您无权查看！");
        }
        Problem problem = problemService.getById(judge.getPid());
        Map<String, Object> result = new HashMap<String, Object>(){{
            put("submission", judge);
        }};
        return ResultJson.success(result);
    }

    @AnonymousPostMapping("/check-submissions-status")
    public ResultJson<Object> checkJudgeResult(@RequestBody SubmitParam submitParam) {
        QueryWrapper<Judge> queryWrapper = new QueryWrapper<>();
        // lambada表达式过滤掉code
        queryWrapper.select(Judge.class, info -> !info.getColumn().equals("code"))
                .in("id", submitParam.getSubmitIds());
        List<Judge> judgeList = judgeService.list(queryWrapper);
        Map<Long, Judge> result = new HashMap<>();
        for (Judge judge : judgeList) {
            result.put(judge.getId(), judge);
        }
        return ResultJson.success(result);
    }

    @PostMapping("/submit-problem-judge")
    @ApiOperation("调用判题系统判题")
    public ResultJson<Judge> submitProblemJudge(@Validated @RequestBody JudgeDto judgeDto) throws Exception {
        Judge judge = judgeService.submitProblemJudge(judgeDto);
        // 启动异步任务获取结果
        judgeService.getJudgeStatus(judge.getId());
        return ResultJson.success(judge);
    }
}
