package com.sise.oj.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.Role;
import com.sise.oj.domain.param.QueryParam;
import com.sise.oj.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author Cijee
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/admin/roles")
@RequiredArgsConstructor
@Api(tags = "系统：角色管理接口")
public class RoleManagerController {
    
    private final RoleService roleService;

    @ApiOperation("分页查询角色")
    @GetMapping
    public ResultJson<Page<Role>> list(QueryParam param, Page<Role> page) {
        return ResultJson.success(roleService.list(param, page));
    }

    @ApiOperation("查询该用户的所有角色信息")
    @GetMapping(params = "uid")
    public ResultJson<List<Role>> get(@RequestParam Long uid) {
        return ResultJson.success(roleService.findByUserId(uid));
    }

    @ApiOperation("新增角色")
    @PostMapping
    public ResultJson<String> create(@Validated @RequestBody Role role) {
        roleService.create(role);
        return ResultJson.success("新增角色成功");
    }

    @ApiOperation("修改角色")
    @PutMapping
    public ResultJson<String> update(@Validated(Role.Update.class) Role role) {
        roleService.update(role);
        return ResultJson.success("更新角色成功");
    }

    @ApiOperation("删除角色")
    @DeleteMapping
    public ResultJson<String> delete(@RequestBody Set<Long> ids) {
        // 验证是否被用户关联
        roleService.verification(ids);
        roleService.delete(ids);
        return ResultJson.success("删除角色成功");
    }
}
