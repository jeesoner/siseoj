package com.sise.oj.controller.admin;

import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.Menu;
import com.sise.oj.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @ApiOperation("返回全部的菜单")
    @GetMapping(value = "/lazy")
    public ResultJson<List<Menu>> query(@RequestParam Long pid){
        return ResultJson.success(menuService.getMenus(pid));
    }

    @ApiOperation("根据菜单ID返回所有子节点ID，包含自身ID")
    @GetMapping(value = "/child")
    public ResultJson<Set<Long>> child(@RequestParam Long id){
        Set<Menu> menuSet = new HashSet<>();
        List<Menu> menuList = menuService.getMenus(id);
        menuSet.add(menuService.findOne(id));
        menuSet = menuService.getChildMenus(menuList, menuSet);
        Set<Long> ids = menuSet.stream().map(Menu::getId).collect(Collectors.toSet());
        return ResultJson.success(ids);
    }
}
