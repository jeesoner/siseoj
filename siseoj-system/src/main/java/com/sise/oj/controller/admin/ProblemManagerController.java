package com.sise.oj.controller.admin;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.Problem;
import com.sise.oj.domain.ProblemCase;
import com.sise.oj.domain.dto.ProblemDto;
import com.sise.oj.domain.param.ProblemQueryParam;
import com.sise.oj.domain.vo.ProblemInfoVo;
import com.sise.oj.exception.BadRequestException;
import com.sise.oj.service.ProblemCaseService;
import com.sise.oj.service.ProblemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
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

    private final ProblemCaseService problemCaseService;

    public ProblemManagerController(ProblemService problemService, ProblemCaseService problemCaseService) {
        this.problemService = problemService;
        this.problemCaseService = problemCaseService;
    }

    @PreAuthorize("@el.check('problem:list')")
    @ApiOperation("查询题目")
    @GetMapping
    public ResultJson<Page<Problem>> query(ProblemQueryParam param, Page<Problem> problemPage) {
        problemPage.setOrders(Collections.singletonList(OrderItem.asc("pid")));
        return ResultJson.success(problemService.adminList(param, problemPage));
    }

    @PreAuthorize("@el.check('problem:list')")
    @ApiOperation("查询题目详细信息")
    @GetMapping("/{id}")
    public ResultJson<ProblemInfoVo> getInfo(@PathVariable Long id) {
        ProblemInfoVo problemInfo = problemService.getInfo(id);
        return ResultJson.success(problemInfo);
    }

    @PreAuthorize("@el.check('problem:list')")
    @ApiOperation("查询题目样例信息")
    @GetMapping("/case")
    public ResultJson<List<ProblemCase>> listProblemCase(@Valid @RequestParam("pid") Long pid) {
        List<ProblemCase> problemCases = problemCaseService.list(Wrappers.lambdaQuery(ProblemCase.class)
                .eq(ProblemCase::getPid, pid)
                .eq(ProblemCase::getStatus, true));
        return ResultJson.success(problemCases);
    }

    @PreAuthorize("@el.check('problem:add')")
    @ApiOperation("新增题目")
    @PostMapping
    public ResultJson<String> save(@Validated @RequestBody ProblemDto problemDto) {
        if (problemDto.getProblem().getPid() != null) {
            throw new BadRequestException("新增题目时不能有ID");
        }
        problemService.insert(problemDto);
        return ResultJson.success("新增题目成功");
    }

    @PreAuthorize("@el.check('problem:edit')")
    @ApiOperation("更新题目")
    @PutMapping
    public ResultJson<String> update(@Validated @RequestBody ProblemDto problemDto) {
        if (problemDto.getProblem().getPid() == null) {
            throw new BadRequestException("更新题目时ID不能为空");
        }
        problemService.update(problemDto);
        return ResultJson.success("更新题目成功");
    }

    @PreAuthorize("@el.check('problem:edit')")
    @ApiOperation("更新题目权限")
    @PutMapping("/auth")
    public ResultJson<String> updateAuth(@RequestBody Problem problem) {
        problemService.updateAuth(problem);
        return ResultJson.success("更新题目权限成功");
    }

    @PreAuthorize("@el.check('problem:del')")
    @ApiOperation("删除题目")
    @DeleteMapping
    public ResultJson<String> delete(@RequestBody Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BadRequestException("删除题目时ID不能为空");
        }
        problemService.delete(ids);
        return ResultJson.success("删除题目成功");
    }
}


