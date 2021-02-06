package com.sise.oj.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.Problem;
import com.sise.oj.domain.param.ProblemQueryParam;
import com.sise.oj.service.ProblemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 题目信息 Controller类
 *
 * @author Cijee
 * @version 1.0
 */
@RestController
@Api(tags = "题库：题目管理接口")
@RequestMapping("/admin/problems")
public class ProblemManagerController {

    private final ProblemService problemService;

    public ProblemManagerController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @ApiOperation("查询题目")
    @GetMapping
    public ResultJson<Page<Problem>> query(ProblemQueryParam param, Page<Problem> problemPage) {
        return ResultJson.success(problemService.queryAll(param, problemPage));
    }

    @ApiOperation("查询题目详细信息")
    @GetMapping("/{id}")
    public ResultJson<Problem> queryProblem(@PathVariable Long id) {
        return ResultJson.success(problemService.findById(id));
    }

    @ApiOperation("新增题目")
    @PostMapping
    public ResultJson<Problem> create(@Validated @RequestBody Problem problem) {
        problemService.create(problem);
        return ResultJson.success(null);
    }

    @ApiOperation("更新题目")
    @PutMapping
    public ResultJson<Problem> update(@Validated(Problem.Update.class) @RequestBody Problem problem) {
        problemService.update(problem);
        return ResultJson.success(null);
    }

    @ApiOperation("删除题目")
    @DeleteMapping
    public ResultJson<Problem> delete(@RequestBody Set<Long> ids) {
        problemService.delete(ids);
        return ResultJson.success(null);
    }
}


