package com.sise.oj.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.annotation.rest.AnonymousGetMapping;
import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.Discuss;
import com.sise.oj.domain.dto.DiscussDto;
import com.sise.oj.domain.param.QueryParam;
import com.sise.oj.service.DiscussService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author Cijee
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "讨论区接口")
@RequestMapping("/discuss")
public class DiscussController {

    private final DiscussService discussService;

    @ApiOperation("分页查询帖子")
    @AnonymousGetMapping
    public ResultJson<Page<DiscussDto>> query(Page<DiscussDto> page, QueryParam param) {
        return ResultJson.success(discussService.query(page, param));
    }

    @ApiOperation("根据ID获取帖子")
    @RequestMapping("/{id}")
    public ResultJson<DiscussDto> get(@PathVariable Long id) {
        DiscussDto discussDto = discussService.findById(id);
        discussService.update(null, Wrappers.lambdaUpdate(Discuss.class)
                .setSql("views=views+1").eq(Discuss::getId,discussDto.getId()));
        return ResultJson.success(discussDto);
    }

    @ApiOperation("创建帖子")
    @PostMapping
    public ResultJson<String> create(@Validated @RequestBody DiscussDto discussDto) {
        discussService.create(discussDto);
        return ResultJson.success(null);
    }

    @ApiOperation("编辑帖子")
    @PutMapping
    public ResultJson<String> update(@Validated(DiscussDto.Update.class) @RequestBody DiscussDto discussDto) {
        discussService.update(discussDto);
        return ResultJson.success(null);
    }

    @ApiOperation("删除帖子")
    @DeleteMapping
    public ResultJson<String> delete(@RequestBody Set<Long> ids) {
        discussService.delete(ids);
        return ResultJson.success(null);
    }
}
