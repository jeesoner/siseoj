package com.sise.oj.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseMapper;
import com.sise.oj.domain.Role;
import com.sise.oj.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author CiJee
 * @version 1.0
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页查询用户列表
     *
     * @param page 分页参数
     * @param keyword 模糊搜索关键字
     * @param enabled 启用状态
     * @return List
     */
    List<User> getUserList(Page<User> page,
                           @Param("keyword") String keyword,
                           @Param("enabled") Boolean enabled);

    /**
     * 根据id获取用户角色
     *
     * @param id 用户主键
     * @return Role
     */
    Role getUserRole(Long id);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return User
     */
    User selectByName(String username);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return User
     */
    User selectByEmail(String email);

    /**
     * 根据角色id查询用户
     *
     * @param id 角色id
     * @return List
     */
    List<User> selectByRoleId(Long id);
}
