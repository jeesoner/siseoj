package com.sise.oj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.annotation.rest.AnonymousGetMapping;
import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.param.ProblemQueryParam;
import com.sise.oj.domain.vo.ProblemInfoVo;
import com.sise.oj.domain.vo.ProblemVo;
import com.sise.oj.service.ProblemService;
import com.sise.oj.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author Cijee
 * @version 1.0
 */
@RestController
@Api(tags = "题目接口")
@RequestMapping("/problems")
public class ProblemController {

    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

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
    public ResultJson<Object> getUserProblemStatus(@RequestBody Set<Long> ids) {
        // 获取当前的用户id
        Long userId = SecurityUtils.getCurrentUserId();

        return null;
    }
}
