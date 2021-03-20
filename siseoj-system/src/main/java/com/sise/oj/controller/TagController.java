package com.sise.oj.controller;

import com.sise.oj.annotation.rest.AnonymousGetMapping;
import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.Tag;
import com.sise.oj.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TagController
 *
 * @author Cijee
 * @version 1.0
 */
@RestController
@Api(tags = "标签接口")
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @ApiOperation("查询所有标签")
    @AnonymousGetMapping("/all")
    public ResultJson<List<Tag>> queryAll() {
        return ResultJson.success(tagService.list());
    }
}
