package com.sise.oj.controller.admin;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.Menu;
import com.sise.oj.domain.param.MenuQueryParam;
import com.sise.oj.domain.vo.MenuVo;
import com.sise.oj.service.MenuService;
import com.sise.oj.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Cijee
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "系统：菜单管理接口")
@RequestMapping("/admin/menus")
public class MenuManagerController {

    private final MenuService menuService;

    @PreAuthorize("@el.check('menu:list')")
    @GetMapping
    @ApiOperation("分页查询菜单")
    public ResultJson<Page<Menu>> queryAll(Page<Menu> page, MenuQueryParam param) {
        return ResultJson.success(menuService.list(page, param));
    }

    @GetMapping("/build")
    @ApiOperation("获取用户所需的菜单")
    public ResultJson<List<MenuVo>> buildMenus() {
        List<Menu> menus;
        // 获取用户的菜单
        if ("root".equals(SecurityUtils.getCurrentUsername())) {
            menus = menuService.list();
        } else {
            menus = menuService.findByUser(SecurityUtils.getCurrentUserId());
        }
        // 将菜单列表构建成菜单树,用于构建前端菜单
        List<Menu> menuList = menuService.buildTree(menus);
        return ResultJson.success(menuService.buildMenus(menuList));
    }

    @ApiOperation("返回全部的菜单")
    @GetMapping("/lazy")
    public ResultJson<List<Menu>> query(@RequestParam Long pid) {
        return ResultJson.success(menuService.getMenus(pid));
    }

    @ApiOperation("根据菜单ID返回所有子节点ID，包含自身ID")
    @GetMapping("/child")
    public ResultJson<Set<Long>> child(@RequestParam Long id) {
        Set<Menu> menuSet = new HashSet<>();
        List<Menu> menuList = menuService.getMenus(id);
        menuSet.add(menuService.findOne(id));
        menuSet = menuService.getChildMenus(menuList, menuSet);
        Set<Long> ids = menuSet.stream().map(Menu::getId).collect(Collectors.toSet());
        return ResultJson.success(ids);
    }

    @ApiOperation("查询菜单:根据ID获取同级与上级数据")
    @PostMapping("/superior")
    public ResultJson<Set<Menu>> getSuperior(@RequestBody List<Long> ids) {
        Set<Menu> menus = new LinkedHashSet<>();
        if (CollectionUtil.isNotEmpty(ids)) {
            for (Long id : ids) {
                Menu menu = menuService.findById(id);
                menus.addAll(menuService.getSuperior(menu, new ArrayList<>()));
            }
            return ResultJson.success(menus);
        }
        return ResultJson.success(menus);
    }

    @PreAuthorize("@el.check('menu:add')")
    @ApiOperation("新增菜单")
    @PostMapping
    public ResultJson<String> create(@Validated(Menu.Create.class) @RequestBody Menu menu) {
        menuService.create(menu);
        return ResultJson.success(null);
    }

    @PreAuthorize("@el.check('menu:edit')")
    @ApiOperation("编辑菜单")
    @PutMapping
    public ResultJson<String> edit(@Validated(Menu.Update.class) @RequestBody Menu menu) {
        menuService.update(menu);
        return ResultJson.success(null);
    }

    @PreAuthorize("@el.check('menu:del')")
    @ApiOperation("删除菜单")
    @DeleteMapping
    public ResultJson<String> delete(@RequestBody Set<Long> ids) {
        menuService.delete(ids);
        return ResultJson.success(null);
    }
}
