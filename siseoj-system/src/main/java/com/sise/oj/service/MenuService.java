package com.sise.oj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseService;
import com.sise.oj.domain.Menu;
import com.sise.oj.domain.param.MenuQueryParam;
import com.sise.oj.domain.vo.MenuVo;

import java.util.List;
import java.util.Set;

/**
 * @author Cijee
 * @version 1.0
 */
public interface MenuService extends BaseService<Menu> {

    /**
     * 分页查询菜单
     *
     * @param page 分页参数
     * @param param 查询参数
     */
    Page<Menu> list(Page<Menu> page, MenuQueryParam param);

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    Menu findById(long id);

    /**
     * 创建菜单
     *
     * @param resources 前端数据
     */
    void create(Menu resources);

    /**
     * 编辑菜单
     *
     * @param resources /
     */
    void update(Menu resources);

    /**
     * 获取所有子节点，包含自身ID
     * @param menuList /
     * @param menuSet /
     * @return /
     */
    Set<Menu> getChildMenus(List<Menu> menuList, Set<Menu> menuSet);

    /**
     * 构建菜单树（给buildMenus使用）
     *
     * @param menus 原始数据
     * @return /
     */
    List<Menu> buildTree(List<Menu> menus);

    /**
     * 构建菜单树
     *
     * @param menus /
     * @return /
     */
    List<MenuVo> buildMenus(List<Menu> menus);

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    Menu findOne(Long id);

    /**
     * 删除菜单
     *
     * @param ids id集合
     */
    void delete(Set<Long> ids);

    /**
     * 懒加载菜单数据
     *
     * @param pid /
     * @return /
     */
    List<Menu> getMenus(Long pid);

    /**
     * 根据ID获取同级与上级数据
     *
     * @param menu /
     * @param getSuperior /
     * @return /
     */
    List<Menu> getSuperior(Menu menu, List<Menu> getSuperior);

    /**
     * 根据当前用户获取菜单
     *
     * @param currentUserId /
     * @return /
     */
    List<Menu> findByUser(Long currentUserId);
}
