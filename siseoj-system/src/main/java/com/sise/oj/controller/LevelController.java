package com.sise.oj.controller;

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
 * LevelController
 *
 * @author Cijee
 * @version 1.0
 */
@RestController
@Api(tags = "难度等级接口")
@RequestMapping("/levels")
public class LevelController {

    private final LevelService levelService;

    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    @ApiOperation("查询所有难度等级")
    @GetMapping
    public ResultJson<List<Level>> queryAll() {
        return ResultJson.success(levelService.list());
    }
}
