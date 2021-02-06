package com.sise.oj.controller;

import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.Source;
import com.sise.oj.service.SourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * SourceController
 *
 * @author Cijee
 * @version 1.0
 */
@RestController
@Api(tags = "来源OJ接口")
@RequestMapping("/sources")
public class SourceController {

    private final SourceService sourceService;

    public SourceController(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    @ApiOperation("查询所有来源OJ")
    @GetMapping("/all")
    public ResultJson<List<Source>> queryAll() {
        return ResultJson.success(sourceService.list());
    }
}