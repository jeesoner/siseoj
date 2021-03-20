package com.sise.oj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.annotation.rest.AnonymousGetMapping;
import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.dto.RankDto;
import com.sise.oj.service.UserRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * RankController
 *
 * @author Cijee
 * @version 1.0
 */
@RestController
@Api(tags = "排名接口")
@RequestMapping("/rank")
@RequiredArgsConstructor
public class RankController {

    private final UserRecordService userRecordService;

    @ApiOperation("查询用户排行榜")
    @AnonymousGetMapping
    public ResultJson<Page<RankDto>> getSolvedRank(String username,
                                                   Page<RankDto> page,
                                                   @RequestParam Integer type) {
        return ResultJson.success(userRecordService.list(username, page, type));
    }
}
