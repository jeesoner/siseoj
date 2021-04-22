package com.sise.oj.mapper;

import com.sise.oj.base.BaseMapper;
import com.sise.oj.domain.Menu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Cijee
 * @version 1.0
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据菜单的 PID 查询
     *
     * @param pid -
     * @return -
     */
    @Select("select * from sys_menu where pid = #{pid}")
    List<Menu> findByPid(long pid);

    /**
     * 查询顶级菜单
     *
     * @return -
     */
    @Select("select * from sys_menu where pid is null")
    List<Menu> findByPidIsNull();

    /**
     * 根据角色ID与菜单类型查询菜单
     * @param roleIds roleIDs
     * @param type 类型
     * @return -
     */
    LinkedHashSet<Menu> findByRoleIdsAndTypeNot(@Param("roleIds") Set<Long> roleIds, @Param("type") int type);

    /**
     * 根据菜单名查询菜单
     *
     * @param title 菜单名称
     * @return Menu
     */
    @Select("select * from sys_menu where title = #{title}")
    Menu findByTitle(String title);

    /**
     * 根据组件名查询菜单
     *
     * @param componentName 组件名
     * @return Menu
     */
    @Select("select * from sys_menu where component_name = #{componentName}")
    Menu findByComponentName(String componentName);

    /**
     * 获取节点的数量
     *
     * @param menuId 菜单ID
     * @return int
     */
    int countByPid(Long menuId);

    /**
     * 更新子节点数量
     *
     * @param count 子节点数量
     * @param menuId 菜单ID
     */
    void updateSubCntById(@Param("count") int count, @Param("menuId") Long menuId);
}
