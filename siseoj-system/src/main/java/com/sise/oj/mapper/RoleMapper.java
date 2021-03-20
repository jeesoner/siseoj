package com.sise.oj.mapper;

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
    Menu getRoleMenu(Long id);

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
}
