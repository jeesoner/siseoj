package com.sise.oj.controller.admin;

import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.Level;
import com.sise.oj.service.LevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 难度级别管理 Controller
 *
 * @author Cijee
 * @version 1.0
 */
@RestController
@Api(tags = "题库：难度级别管理")
@RequestMapping("/admin/levels")
public class LevelManagerController {

    private final LevelService levelService;

    public LevelManagerController(LevelService levelService) {
        this.levelService = levelService;
    }

    @ApiOperation("查询所有难度级别")
    @GetMapping("/all")
    public ResultJson<List<Level>> query() {
        return ResultJson.success(levelService.list());
    }
}
