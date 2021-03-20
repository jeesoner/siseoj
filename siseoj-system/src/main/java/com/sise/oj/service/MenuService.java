package com.sise.oj.service;

import com.sise.oj.base.BaseService;
import com.sise.oj.domain.Menu;
import com.sise.oj.domain.param.MenuQueryParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author Cijee
 * @version 1.0
 */
public interface MenuService extends BaseService<Menu> {

    /**
     * 查询全部数据
     * @param param 条件
     * @param isQuery /
     * @throws Exception /
     * @return /
     */
    List<Menu> queryAll(MenuQueryParam param, Boolean isQuery) throws Exception;

    /**
     * 根据ID查询
     * @param id /
     * @return /
     */
    Menu findById(long id);

    /**
     * 创建
     * @param resources /
     */
    void create(Menu resources);

    /**
     * 编辑
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
     * 构建菜单树
     * @param menus 原始数据
     * @return /
     */
    List<Menu> buildTree(List<Menu> menus);

    /**
     * 构建菜单树
     * @param menus /
     * @return /
     */
    Object buildMenus(List<Menu> menus);

    /**
     * 根据ID查询
     * @param id /
     * @return /
     */
    Menu findOne(Long id);

    /**
     * 删除
     * @param menuSet /
     */
    void delete(Set<Menu> menuSet);

    /**
     * 导出
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<Menu> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 懒加载菜单数据
     * @param pid /
     * @return /
     */
    List<Menu> getMenus(Long pid);

    /**
     * 根据ID获取同级与上级数据
     * @param menu /
     * @param objects /
     * @return /
     */
    List<Menu> getSuperior(Menu menu, List<Menu> objects);

    /**
     * 根据当前用户获取菜单
     * @param currentUserId /
     * @return /
     */
    List<Menu> findByUser(Long currentUserId);
}
