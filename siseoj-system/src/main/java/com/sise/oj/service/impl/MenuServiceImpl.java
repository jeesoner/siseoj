package com.sise.oj.service.impl;

import com.sise.oj.base.BaseServiceImpl;
import com.sise.oj.domain.Menu;
import com.sise.oj.domain.Role;
import com.sise.oj.domain.param.MenuQueryParam;
import com.sise.oj.mapper.MenuMapper;
import com.sise.oj.service.MenuService;
import com.sise.oj.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
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
    public List<Menu> queryAll(MenuQueryParam param, Boolean isQuery) throws Exception {
        return null;
    }

    @Override
    public Menu findById(long id) {
        return null;
    }

    @Override
    public void create(Menu resources) {

    }

    @Override
    public void update(Menu resources) {

    }

    @Override
    public Set<Menu> getChildMenus(List<Menu> menuList, Set<Menu> menuSet) {
        for (Menu menu : menuList) {
            menuSet.add(menu);
            List<Menu> menus = menuMapper.findByPid(menu.getId());
            if(menus != null && menus.size() != 0){
                // 递归
                getChildMenus(menus, menuSet);
            }
        }
        return menuSet;
    }

    @Override
    public List<Menu> buildTree(List<Menu> menus) {
        return null;
    }

    @Override
    public Object buildMenus(List<Menu> menus) {
        return null;
    }

    @Override
    public Menu findOne(Long id) {
        return menuMapper.selectById(id);
    }

    @Override
    public void delete(Set<Menu> menuSet) {

    }

    @Override
    public void download(List<Menu> queryAll, HttpServletResponse response) throws IOException {

    }

    @Override
    public List<Menu> getMenus(Long pid) {
        if (pid != null && !pid.equals(0L)) {
            return menuMapper.findByPid(pid);
        } else {
            return menuMapper.findByPidIsNull();
        }
    }

    @Override
    public List<Menu> getSuperior(Menu menu, List<Menu> objects) {
        return null;
    }

    @Override
    public List<Menu> findByUser(Long currentUserId) {
        List<Role> roles = roleService.findByUserId(currentUserId);
        Set<Long> roleIds = roles.stream().map(Role::getId).collect(Collectors.toSet());
        return null;
    }
}
