package com.sise.oj.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseService;
import com.sise.oj.domain.User;
import com.sise.oj.domain.param.QueryParam;

import java.util.Set;

/**
 * 用户服务接口
 *
 * @author CiJee
 * @version 1.0
 */
public interface UserService extends BaseService<User> {

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return User
     */
    User findByName(String username);

    /**
     * 根据主键查找用户
     *
     * @param id 主键
     * @return User
     */
    User findById(Long id);

    /**
     * 分页查询用户
     *
     * @param param 查询参数
     * @param page 分页参数
     * @return Page
     */
    Page<User> list(QueryParam param, Page<User> page);

    /**
     * 创建用户
     *
     * @param resources 用户实体
     */
    void create(User resources);

    /**
     * 授予用户角色
     *
     * @param resources 用户实体
     */
    void grantAuthorization(User resources);

    /**
     * 更新用户
     *
     * @param resources 用户
     */
    void update(User resources);

    /**
     * 删除用户
     *
     * @param ids 用户主键集合
     */
    void delete(Set<Long> ids);
}
