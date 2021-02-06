package com.sise.oj.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.Tag;
import com.sise.oj.domain.param.TagQueryParam;
import com.sise.oj.enums.ResultCode;
import com.sise.oj.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 标签管理 Controller
 *
 * @author Cijee
 * @version 1.0
 */
@RestController
@Api(tags = "题库：标签管理")
@RequestMapping("/admin/tags")
public class TagManagerController {

    private final TagService tagService;

    public TagManagerController(TagService tagService) {
        this.tagService = tagService;
    }

    @ApiOperation("查询标签")
    @GetMapping
    public ResultJson<Page<Tag>> query(TagQueryParam param, Page<Tag> page) {
        return ResultJson.success(tagService.query(param, page));
    }

    @ApiOperation("查询所有标签")
    @GetMapping("/all")
    public ResultJson<List<Tag>> queryAll() {
        return ResultJson.success(tagService.list());
    }

    @ApiOperation("新增标签")
    @PostMapping
    public ResultJson<Tag> create(@Validated @RequestBody Tag tag) {
        tagService.create(tag);
        return ResultJson.success(null);
    }

    @ApiOperation("更新标签")
    @PutMapping
    public ResultJson<Tag> update(@Validated(Tag.Update.class) @RequestBody Tag tag) {
        tagService.update(tag);
        return ResultJson.success(null);
    }

    @ApiOperation("删除标签")
    @DeleteMapping
    public ResultJson<Tag> delete(@RequestBody Set<Long> ids) {
        // 如有删除的标签
        if (!CollectionUtils.isEmpty(ids)) {
            tagService.delete(ids);
            return ResultJson.success(null);
        } else {
            return ResultJson.failure(ResultCode.PARAM_ERROR, "没有需要删除的标签");
        }
    }
}
