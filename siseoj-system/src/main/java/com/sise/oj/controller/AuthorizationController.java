package com.sise.oj.controller;

import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.dto.AuthUserDTO;
import com.sise.oj.enums.ResultCode;
import com.sise.oj.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 授权、根据token获取用户详细信息
 *
 * @author CiJee
 * @version 1.0
 */
@Api(tags = "系统：系统授权接口")
@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private final UserInfoService userInfoService;

    public AuthorizationController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @ApiOperation("登录授权")
    @PostMapping("/login")
    public ResultJson<String> login(@Validated @RequestBody AuthUserDTO authUser) {
        boolean ok = userInfoService.login(authUser);
        if (ok) {
            return ResultJson.failure(ResultCode.SUCCESS);
        } else {
            return ResultJson.failure(ResultCode.USER_LOGIN_ERROR);
        }
    }
}
