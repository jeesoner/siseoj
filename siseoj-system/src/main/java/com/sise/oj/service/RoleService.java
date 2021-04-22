package com.sise.oj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseService;
import com.sise.oj.domain.Role;
import com.sise.oj.domain.User;
import com.sise.oj.domain.param.QueryParam;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

/**
 * @author Cijee
 * @version 1.0
 */
public interface RoleService extends BaseService<Role> {

    /**
     * 获取用户的权限信息
     *
     * @param user 用户
     * @return 权限信息列表
     */
    List<GrantedAuthority> mapToGrantedAuthorities(User user);

    /**
     * 更新用户的角色信息
     *
     * @param roles 角色集合
     */
    void updateUserRole(Long uid, Set<Role> roles);

    /**
     * 分页查询角色
     *
     * @param param 查询参数
     * @param page 分页参数
     * @return Page
     */
    Page<Role> list(QueryParam param, Page<Role> page);

    /**
     * 根据id查询角色
     *
     * @param id 角色id
     * @return Role
     */
    Role findById(Long id);

    /**
     * 创建角色
     *
     * @param resources Role
     */
    void create(Role resources);

    /**
     * 更新角色
     *
     * @param resources Role
     */
    void update(Role resources);

    /**
     * 删除角色
     *
     * @param ids id集合
     */
    void delete(Set<Long> ids);

    /**
     * 验证是否被用户关联
     *
     * @param ids /
     */
    void verification(Set<Long> ids);

    /**
     * 根据用户id查询角色
     *
     * @param uid 用户id
     * @return List
     */
    Set<Role> findByUserId(Long uid);

    /**
     * 更新角色菜单
     *
     * @param resources 旧角色
     * @param role 新角色
     */
    void updateMenu(Role resources, Role role);

    /**
     *解绑角色菜单
     *
     * @param menuId 菜单id
     */
    void untiedMenu(Long menuId);
}
