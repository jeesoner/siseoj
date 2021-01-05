package com.sise.oj.service.impl;

import com.sise.oj.domain.dto.JwtUserDto;
import com.sise.oj.service.OnlineUserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cijee
 * @version 1.0
 */
@Service
public class OnlineUserServiceImpl implements OnlineUserService {

    /**
     * 保存在线用户
     *
     * @param jwtUserDto 授权用户信息 dto
     * @param token token
     * @param request 请求
     */
    @Override
    public void save(JwtUserDto jwtUserDto, String token, HttpServletRequest request) {

    }

    /**
     * 退出登录
     *
     * @param token token
     */
    @Override
    public void logout(String token) {

    }
}
