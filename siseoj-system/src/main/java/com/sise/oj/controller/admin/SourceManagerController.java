package com.sise.oj.controller.admin;

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
 * 来源OJ管理 Controller
 *
 * @author Cijee
 * @version 1.0
 */
@RestController
@Api(tags = "题库：来源OJ管理")
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
}
