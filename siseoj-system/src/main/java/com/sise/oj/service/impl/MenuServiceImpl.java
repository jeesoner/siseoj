package com.sise.oj.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseServiceImpl;
import com.sise.oj.domain.Menu;
import com.sise.oj.domain.Role;
import com.sise.oj.domain.param.MenuQueryParam;
import com.sise.oj.domain.vo.MenuMetaVo;
import com.sise.oj.domain.vo.MenuVo;
import com.sise.oj.exception.BadRequestException;
import com.sise.oj.exception.DataExistException;
import com.sise.oj.mapper.MenuMapper;
import com.sise.oj.service.MenuService;
import com.sise.oj.service.RoleService;
import com.sise.oj.util.StringUtils;
import com.sise.oj.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Cijee
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements MenuService {

    private final MenuMapper menuMapper;
    private final RoleService roleService;

    @Override
    public Page<Menu> list(Page<Menu> page, MenuQueryParam param) {
        LambdaQueryWrapper<Menu> wrapper = Wrappers.lambdaQuery(Menu.class);
        if (StringUtils.isNotBlank(param.getKeyword())) {
            wrapper.like(Menu::getTitle, param.getKeyword());
            if (Objects.isNull(param.getPid())) {
                wrapper.isNull(Menu::getPid);
            }
        }
        if (Objects.nonNull(param.getPid())) { // 父节点不为空
            wrapper.like(Menu::getPid, param.getPid());
            page.setSize(999);
        } else {
            wrapper.isNull(Menu::getPid);
        }
        wrapper.orderByAsc(Menu::getMenuSort);
        return menuMapper.selectPage(page, wrapper);
    }

    @Override
    public Menu findById(long id) {
        Menu menu = menuMapper.selectById(id);
        ValidationUtils.isNull(menu, "Menu", "id", id);
        return menu;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Menu resources) {
        if (menuMapper.findByTitle(resources.getTitle()) != null) {
            throw new DataExistException(Menu.class, "title", resources.getTitle());
        }
        if (StringUtils.isNotBlank(resources.getComponentName())) {
            if (menuMapper.findByComponentName(resources.getComponentName()) != null) {
                throw new DataExistException(Menu.class, "componentName", resources.getComponentName());
            }
        }
        if (resources.getPid().equals(0L)) {
            resources.setPid(null);
        }
        // 外链格式校验
        if (resources.getIFrame()) {
            String http = "http://", https = "https://";
            if (!resources.getPath().toLowerCase().startsWith(http) || !resources.getPath().toLowerCase().startsWith(https)) {
                throw new BadRequestException("外链必须以http://或者https://开头");
            }
        }
        resources.setSubCount(0);
        menuMapper.insert(resources);
        // 计算子节点的数量
        updateSubCnt(resources.getPid());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Menu resources) {
        if (resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        Menu menu = menuMapper.selectById(resources.getId());
        ValidationUtils.isNull(menu, "菜单", "ID", menu.getId());

        Menu menu1 = menuMapper.findByTitle(resources.getTitle());
        if (menu1 != null && !menu1.getId().equals(menu.getId())) {
            throw new DataExistException(Menu.class, "title", resources.getTitle());
        }
        if (StringUtils.isNotBlank(resources.getComponentName())) {
            menu1 = menuMapper.findByComponentName(resources.getComponentName());
            if (menu1 != null && !menu1.getId().equals(menu.getId())) {
                throw new DataExistException(Menu.class, "componentName", resources.getComponentName());
            }
        }
        if (resources.getPid().equals(0L)) {
            resources.setPid(null);
        }
        // 外链格式校验
        if (resources.getIFrame()) {
            String http = "http://", https = "https://";
            if (!resources.getPath().toLowerCase().startsWith(http) || !resources.getPath().toLowerCase().startsWith(https)) {
                throw new BadRequestException("外链必须以http://或者https://开头");
            }
        }
        // 记录的父节点ID
        Long oldPid = menu.getPid();
        Long newPid = resources.getPid();

        menu.setTitle(resources.getTitle());
        menu.setComponent(resources.getComponent());
        menu.setPath(resources.getPath());
        menu.setIcon(resources.getIcon());
        menu.setIFrame(resources.getIFrame());
        menu.setPid(resources.getPid());
        menu.setMenuSort(resources.getMenuSort());
        menu.setCache(resources.getCache());
        menu.setHidden(resources.getHidden());
        menu.setComponentName(resources.getComponentName());
        menu.setPermission(resources.getPermission());
        menu.setType(resources.getType());
        menuMapper.updateById(menu);
        // 计算父级菜单节点数目
        updateSubCnt(oldPid);
        updateSubCnt(newPid);
    }

    @Override
    public Set<Menu> getChildMenus(List<Menu> menuList, Set<Menu> menuSet) {
        for (Menu menu : menuList) {
            menuSet.add(menu);
            List<Menu> menus = menuMapper.findByPid(menu.getId());
            if (menus != null && menus.size() != 0) {
                // 递归
                getChildMenus(menus, menuSet);
            }
        }
        return menuSet;
    }

    @Override
    public List<Menu> buildTree(List<Menu> menus) {
        List<Menu> trees = new ArrayList<>();
        Set<Long> ids = new HashSet<>();
        for (Menu menu : menus) {
            // 添加顶层节点
            if (menu.getPid() == null) {
                trees.add(menu);
            }
            for (Menu it : menus) {
                // 如果当前节点有叶子节点，则将其他节点添加到其子节点集合中
                if (menu.getId().equals(it.getPid())) {
                    if (menu.getMenus() == null) {
                        menu.setMenus(new ArrayList<>());
                    }
                    menu.getMenus().add(it);
                    // 添加叶子节点
                    ids.add(it.getId());
                }
            }
        }
        // 如果没有顶层节点
        if (trees.size() == 0) {
            // 菜单列表节点id与叶子节点id不一样的
            trees = menus.stream().filter(menu -> !ids.contains(menu.getId())).collect(Collectors.toList());
        }
        return trees;
    }

    @Override
    public List<MenuVo> buildMenus(List<Menu> menus) {
        List<MenuVo> list = new LinkedList<>();
        for (Menu menu : menus) {
            if (menu != null) {
                List<Menu> menuList = menu.getMenus();
                // 构造返回给前端的路由Vo
                MenuVo menuVo = new MenuVo();
                menuVo.setName(StringUtils.isNotBlank(menu.getComponentName()) ? menu.getComponentName() : menu.getTitle());
                // 一级目录需要加 /
                menuVo.setPath(menu.getPid() == null ? "/" + menu.getPath() : menu.getPath());
                menuVo.setHidden(menu.getHidden());
                // 如果不是外链接
                if (!menu.getIFrame()) {
                    // 如果是一级菜单
                    if (menu.getPid() == null) {
                        menuVo.setComponent(StringUtils.isEmpty(menu.getComponent()) ? "Layout" : menu.getComponent());
                    }
                    // 如果不是一级菜单，且菜单类型是目录，则是多级菜单
                    else if (menu.getType() == 0) {
                        menuVo.setComponent(StringUtils.isEmpty(menu.getComponent()) ? "ParentView" : menu.getComponent());
                    } else if (StringUtils.isNoneBlank(menu.getComponent())) {
                        menuVo.setComponent(menu.getComponent());
                    }
                }
                menuVo.setMeta(new MenuMetaVo(menu.getTitle(), menu.getIcon(), menu.getCache()));
                if (CollectionUtil.isNotEmpty(menuList)) {
                    menuVo.setAlwaysShow(true);
                    menuVo.setRedirect(menu.getRedirect());
                    menuVo.setChildren(buildMenus(menuList));
                    // 处理是一级菜单并且没有子菜单的情况
                } else if (menu.getPid() == null) {
                    MenuVo menuVo1 = new MenuVo();
                    menuVo1.setMeta(menuVo.getMeta());
                    // 非外链
                    if (!menu.getIFrame()) {
                        menuVo1.setPath("index");
                        menuVo1.setName(menuVo.getName());
                        menuVo1.setComponent(menuVo.getComponent());
                    } else {
                        menuVo1.setPath(menu.getPath());
                    }
                    menuVo.setName(null);
                    menuVo.setMeta(null);
                    menuVo.setComponent("Layout");
                    List<MenuVo> list1 = new ArrayList<>();
                    list1.add(menuVo1);
                    menuVo.setChildren(list1);
                }
                list.add(menuVo);
            }
        }
        return list;
    }

    @Override
    public Menu findOne(Long id) {
        return menuMapper.selectById(id);
    }

    @Override
    public void delete(Set<Long> ids) {
        menuMapper.deleteBatchIds(ids);
    }

    @Override
    public List<Menu> getMenus(Long pid) {
        if (pid != null && !pid.equals(0L)) {
            // 查询该节点下一层的所有节点（懒加载）
            return menuMapper.findByPid(pid);
        } else {
            // 查询顶层菜单
            return menuMapper.findByPidIsNull();
        }
    }

    @Override
    public List<Menu> getSuperior(Menu menu, List<Menu> menus) {
        // 如果是顶级节点，直接返回所有的顶级节点
        if (menu.getPid() == null) {
            menus.addAll(menuMapper.findByPidIsNull());
            return menus;
        }
        // 添加上级节点
        menus.addAll(menuMapper.findByPid(menu.getPid()));
        return getSuperior(findById(menu.getPid()), menus);
    }

    @Override
    public List<Menu> findByUser(Long currentUserId) {
        Set<Role> roles = roleService.findByUserId(currentUserId);
        Set<Long> roleIds = roles.stream().map(Role::getId).collect(Collectors.toSet());
        LinkedHashSet<Menu> menus = menuMapper.findByRoleIdsAndTypeNot(roleIds, 2);
        return new ArrayList<>(menus);
    }

    /**
     * 更新子节点的数量
     */
    public void updateSubCnt(Long menuId) {
        if (menuId != null) {
            int count = menuMapper.countByPid(menuId);
            menuMapper.updateSubCntById(count, menuId);
        }
    }
}
