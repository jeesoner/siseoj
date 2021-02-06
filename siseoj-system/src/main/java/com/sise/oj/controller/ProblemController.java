package com.sise.oj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.Problem;
import com.sise.oj.domain.param.ProblemQueryParam;
import com.sise.oj.service.ProblemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation("查询题目")
    @GetMapping
    public ResultJson<Page<Problem>> query(ProblemQueryParam param, Page<Problem> page) {
        return ResultJson.success(problemService.queryAll(param, page));
    }

    @ApiOperation("查询题目详细信息")
    @GetMapping("/{id}")
    public ResultJson<Problem> queryProblem(@PathVariable Long id) {
        return ResultJson.success(problemService.findById(id));
    }
}
