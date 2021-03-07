package com.sise.oj.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.Tag;
import com.sise.oj.domain.param.QueryParam;
import com.sise.oj.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "题库：标签管理接口")
@RequestMapping("/admin/tags")
public class TagManagerController {

    private final TagService tagService;

    public TagManagerController(TagService tagService) {
        this.tagService = tagService;
    }

    @ApiOperation("查询标签")
    @GetMapping
    public ResultJson<Page<Tag>> query(QueryParam param, Page<Tag> page) {
        return ResultJson.success(tagService.query(param, page));
    }

    @ApiOperation("查询所有标签")
    @GetMapping("/all")
    public ResultJson<List<Tag>> queryAll() {
        return ResultJson.success(tagService.list());
    }

    @ApiOperation("新增标签")
    @PostMapping
    public ResultJson<String> create(@Validated @RequestBody Tag tag) {
        tagService.create(tag);
        return ResultJson.success("新增标签成功");
    }

    @ApiOperation("修改标签")
    @PutMapping
    public ResultJson<String> update(@Validated(Tag.Update.class) @RequestBody Tag tag) {
        tagService.update(tag);
        return ResultJson.success("更新标签成功");
    }

    @ApiOperation("删除标签")
    @DeleteMapping
    public ResultJson<String> delete(@RequestBody Set<Long> ids) {
        // 验证是否被题目关联
        tagService.verification(ids);
        tagService.delete(ids);
        return ResultJson.success("删除标签成功");
    }
}
