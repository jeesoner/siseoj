package com.sise.oj.service;


import com.sise.oj.domain.dto.JwtUserDto;
import com.sise.oj.domain.dto.OnlineUserDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
public interface OnlineUserService {

    /**
     * 查询全部数据不分页
     *
     * @param filter 过滤关键词
     * @return List
     */
    List<OnlineUserDto> getAll(String filter);

    /**
     * 保存在线用户信息
     *
     * @param jwtUserDto 授权用户信息 dto
     * @param token token
     * @param request 请求
     */
    void save(JwtUserDto jwtUserDto, String token, HttpServletRequest request);

    /**
     * 退出登录
     *
     * @param token token
     */
    void logout(String token);

    /**
     * 踢出用户
     *
     * @param token token
     */
    void kickOut(String token);

    /**
     * 根据用户名强退用户
     *
     * @param username 用户名
     */
    void kickOutForUsername(String username);

    /**
     * 获取在线用户信息
     *
     * @param key -
     * @return -
     */
    OnlineUserDto getOne(String key);
}
