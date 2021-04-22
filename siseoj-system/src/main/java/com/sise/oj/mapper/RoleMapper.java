package com.sise.oj.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseMapper;
import com.sise.oj.domain.Menu;
import com.sise.oj.domain.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author Cijee
 * @version 1.0
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 分页查询角色信息
     *
     * @param page 分页参数
     * @param keyword 模糊查询
     * @return List
     */
    List<Role> findByPage(@Param("page") Page<Role> page, @Param("keyword") String keyword);

    /**
     * 获取用户关联的所有角色
     *
     * @param id 用户id
     * @return 角色集合
     */
    Set<Role> findByUserId(Long id);

    /**
     * 根据角色ID获取菜单
     *
     * @param id 角色id
     * @return Menu
     */
    Set<Menu> getRoleMenu(Long id);

    /**
     * 删除用户关联角色
     *
     * @param uid 用户id
     */
    void deleteUserRoleById(@Param("uid") Long uid);

    /**
     * 增加用户关联角色
     *
     * @param uid 用户id
     * @param ids 角色id集合
     */
    void insertUserRole(@Param("uid")Long uid, @Param("ids") List<Long> ids);

    /**
     * 根据用户id查询
     *
     * @param uid 用户id
     * @return List
     */
    List<Role> selectByUserId(Long uid);

    /**
     * 该角色关联的用户数
     *
     * @param ids 角色id集合
     */
    int countByRoles(@Param("ids") Set<Long> ids);

    /**
     * 解绑角色菜单
     *
     * @param menuId 菜单id
     */
    void untiedMenu(Long menuId);

    /**
     * 根据角色id解绑菜单
     *
     * @param id 角色id
     */
    void untiedMenuByRoleId(Long id);

    /**
     * 根据角色id绑定菜单
     *
     * @param rid 角色id
     * @param ids 菜单id集合
     */
    void bindMenuByRoleId(@Param("rid") Long rid, @Param("ids") Set<Long> ids);
}
