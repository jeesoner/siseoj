package com.sise.oj.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.Source;
import com.sise.oj.domain.param.QueryParam;
import com.sise.oj.enums.ResultCode;
import com.sise.oj.service.SourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 题目来源管理 Controller
 *
 * @author Cijee
 * @version 1.0
 */
@RestController
@Api(tags = "题库：题目来源管理接口")
@RequestMapping("/admin/sources")
public class SourceManagerController {

    private final SourceService sourceService;

    public SourceManagerController(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    @ApiOperation("查询所有来源OJ")
    @GetMapping("/all")
    public ResultJson<List<Source>> query() {
        return ResultJson.success(sourceService.list());
    }

    @ApiOperation("查询题目来源")
    @GetMapping
    public ResultJson<Page<Source>> query(QueryParam param, Page<Source> page) {
        return ResultJson.success(sourceService.query(param, page));
    }

    @ApiOperation("新增题目来源")
    @PostMapping
    public ResultJson<Source> create(@Validated @RequestBody Source source) {
        sourceService.create(source);
        return ResultJson.success(null);
    }

    @ApiOperation("修改题目来源")
    @PutMapping
    public ResultJson<Source> update(@Validated(Source.Update.class) @RequestBody Source source) {
        sourceService.update(source);
        return ResultJson.success(null);
    }

    @ApiOperation("删除题目来源")
    @DeleteMapping
    public ResultJson<Source> delete(@RequestBody Set<Long> ids) {
        // 如有删除的题目来源
        if (!CollectionUtils.isEmpty(ids)) {
            sourceService.delete(ids);
            return ResultJson.success(null);
        } else {
            return ResultJson.failure(ResultCode.BAD_REQUEST, "没有需要删除的题目来源");
        }
    }
}
