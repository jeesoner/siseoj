package com.sise.oj.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseService;
import com.sise.oj.domain.User;
import com.sise.oj.domain.param.QueryParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
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
     * 管理员更新用户
     *
     * @param resources 用户实体
     */
    void adminUpdate(User resources);

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

    /**
     * 更新密码
     *
     * @param username 用户名
     * @param encodePassword 编码后的密码
     */
    void updatePassword(String username, String encodePassword);

    /**
     * 更新邮箱
     *
     * @param username 用户名
     * @param newEmail 新的邮箱
     */
    void updateEmail(String username, String newEmail);

    /**
     * 更新头像
     *
     * @param avatar 头像
     * @return Map
     */
    Map<String, String> updateAvatar(MultipartFile avatar);
}
