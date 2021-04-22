package com.sise.oj.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseServiceImpl;
import com.sise.oj.domain.Menu;
import com.sise.oj.domain.Role;
import com.sise.oj.domain.User;
import com.sise.oj.domain.param.QueryParam;
import com.sise.oj.exception.BadRequestException;
import com.sise.oj.exception.DataExistException;
import com.sise.oj.mapper.RoleMapper;
import com.sise.oj.service.RoleService;
import com.sise.oj.util.StringUtils;
import com.sise.oj.util.ValidationUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色服务 实现类
 *
 * @author Cijee
 * @version 1.0
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    /**
     * 获取用户的权限信息
     *
     * @param user 用户
     * @return 权限信息列表
     */
    @Override
    public List<GrantedAuthority> mapToGrantedAuthorities(User user) {
        Set<String> permissions = new HashSet<>();
        // 如果是root用户直接返回 root用户具有最高权限
        if ("root".equals(user.getUsername())) {
            permissions.add("root");
            return permissions.stream().map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
        // 用户角色集合
        Set<Role> roles = roleMapper.findByUserId(user.getId());
        // 无角色，权限为空
        if (CollectionUtils.isEmpty(roles)) {
            return new ArrayList<>();
        }
        //permissions = roleMapper.findPermissionByIds(ids)
        //        .stream().map(Permission::getPermission).collect(Collectors.toSet());
        // 获取角色的权限信息
        permissions = roles.stream().flatMap(role -> role.getMenus().stream())
                .filter(menu -> StringUtils.isNotBlank(menu.getPermission()))
                .map(Menu::getPermission).collect(Collectors.toSet());
        // 转换成List
        return permissions.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserRole(Long uid, Set<Role> roles) {
        roleMapper.deleteUserRoleById(uid);
        if (!CollectionUtils.isEmpty(roles)) {
            roleMapper.insertUserRole(uid, roles.stream().map(Role::getId).collect(Collectors.toList()));
        }
    }

    @Override
    public Page<Role> list(QueryParam param, Page<Role> page) {
        List<Role> roles = roleMapper.findByPage(page, param.getKeyword());
        page.setRecords(roles);
        return page;
    }

    @Override
    public Role findById(Long id) {
        Role role = roleMapper.selectById(id);
        Set<Menu> menus = roleMapper.getRoleMenu(id);
        role.setMenus(menus);
        return role;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Role resources) {
        // 创建用户前，先查看有没有重复的用户名
        if (roleMapper.selectOne(Wrappers.lambdaQuery(Role.class).eq(Role::getName, resources.getName())) != null) {
            throw new DataExistException("该角色名已存在");
        }
        roleMapper.insert(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Role resources) {
        Role role = roleMapper.selectById(resources.getId());
        ValidationUtils.isNull(role, "角色", "id", resources.getId());
        Role role1 = roleMapper.selectOne(Wrappers.lambdaQuery(Role.class).eq(Role::getName, resources.getName()));
        if (role1 != null && !role.getName().equals(role1.getName())) {
            throw new DataExistException("该角色名已经被用过了");
        }
        role.setName(resources.getName());
        role.setDescription(resources.getDescription());
        roleMapper.updateById(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        roleMapper.deleteBatchIds(ids);
    }

    @Override
    public void verification(Set<Long> ids) {
        if (roleMapper.countByRoles(ids) > 0) {
            throw new BadRequestException("所选角色存在用户关联，请解除关联再试！");
        }
    }

    @Override
    public Set<Role> findByUserId(Long uid) {
        return roleMapper.findByUserId(uid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(Role resources, Role role) {
        roleMapper.untiedMenuByRoleId(role.getId());
        // 更新角色菜单信息
        if (role.getId() != null) {
            // 解绑菜单
            roleMapper.untiedMenuByRoleId(role.getId());
        }
        // 绑定菜单
        Set<Long> ids = resources.getMenus().stream().map(Menu::getId).collect(Collectors.toSet());
        roleMapper.bindMenuByRoleId(role.getId(), ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void untiedMenu(Long menuId) {
        // 更新菜单
        roleMapper.untiedMenu(menuId);
    }
}
