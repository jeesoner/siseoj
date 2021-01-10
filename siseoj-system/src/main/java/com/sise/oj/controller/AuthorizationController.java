package com.sise.oj.controller;

import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.dto.AuthUserDto;
import com.sise.oj.domain.dto.JwtUserDto;
import com.sise.oj.security.TokenProvider;
import com.sise.oj.security.bean.LoginProperties;
import com.sise.oj.service.OnlineUserService;
import com.sise.oj.service.UserService;
import com.sise.oj.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 授权、根据token获取用户详细信息
 *
 * @author CiJee
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Api(tags = "系统：系统授权接口")
public class AuthorizationController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final OnlineUserService onlineUserService;
    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final RedisUtils redisUtils;
    @Resource
    private LoginProperties loginProperties;

    @ApiOperation("登录授权")
    @PostMapping("/login")
    public ResultJson<Object> login(@Validated @RequestBody AuthUserDto authUser, HttpServletRequest request) throws Exception {
        // 获取密码
        String password = authUser.getPassword();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authUser.getUsername(), password);
        AuthenticationManager object = authenticationManagerBuilder.getObject();
        Authentication authentication = object.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌
        String token = tokenProvider.createToken(authentication);
        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
        // 保存用户在线信息
        // TODO
        // 返回 token 和用户信息
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
            put("token", token);
            put("user", jwtUserDto);
        }};
        return ResultJson.success(authInfo);
    }

    public ResultJson<?> logout() {
        return ResultJson.success(null);
    }
}
