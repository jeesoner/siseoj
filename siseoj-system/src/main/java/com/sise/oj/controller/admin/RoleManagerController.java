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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("@el.check('role:list')")
    @ApiOperation("分页查询角色")
    @GetMapping
    public ResultJson<Page<Role>> list(QueryParam param, Page<Role> page) {
        return ResultJson.success(roleService.list(param, page));
    }

    @PreAuthorize("@el.check('role:list')")
    @ApiOperation("获取单个角色")
    @GetMapping("/{id}")
    public ResultJson<Role> get(@PathVariable Long id) {
        return ResultJson.success(roleService.findById(id));
    }

    @PreAuthorize("@el.check('role:list')")
    @ApiOperation("查询该用户的所有角色信息")
    @GetMapping(params = "uid")
    public ResultJson<Set<Role>> getRolesByUid(@RequestParam Long uid) {
        return ResultJson.success(roleService.findByUserId(uid));
    }

    @PreAuthorize("@el.check('role:add')")
    @ApiOperation("新增角色")
    @PostMapping
    public ResultJson<String> create(@Validated @RequestBody Role role) {
        roleService.create(role);
        return ResultJson.success("新增角色成功");
    }

    @PreAuthorize("@el.check('role:edit')")
    @ApiOperation("修改角色")
    @PutMapping
    public ResultJson<String> update(@Validated(Role.Update.class) Role role) {
        roleService.update(role);
        return ResultJson.success("更新角色成功");
    }

    @PreAuthorize("@el.check('role:del')")
    @ApiOperation("删除角色")
    @DeleteMapping
    public ResultJson<String> delete(@RequestBody Set<Long> ids) {
        // 验证是否被用户关联
        roleService.verification(ids);
        roleService.delete(ids);
        return ResultJson.success("删除角色成功");
    }

    @PreAuthorize("@el.check('role:edit')")
    @ApiOperation("修改角色菜单")
    @PutMapping("/menu")
    public ResultJson<String> editMenu(@RequestBody Role resources) {
        Role role = roleService.getById(resources.getId());
        // 更新菜单
        roleService.updateMenu(resources, role);
        return ResultJson.success(null);
    }
}
