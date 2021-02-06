package com.sise.oj.controller;

import cn.hutool.core.util.IdUtil;
import com.sise.oj.annotation.rest.AnonymousGetMapping;
import com.sise.oj.base.ResultJson;
import com.sise.oj.config.RsaProperties;
import com.sise.oj.domain.dto.AuthUserDto;
import com.sise.oj.domain.dto.JwtUserDto;
import com.sise.oj.exception.BadRequestException;
import com.sise.oj.security.TokenProvider;
import com.sise.oj.security.bean.LoginProperties;
import com.sise.oj.security.bean.SecurityProperties;
import com.sise.oj.service.OnlineUserService;
import com.sise.oj.util.RedisUtils;
import com.sise.oj.util.RsaUtils;
import com.sise.oj.util.SecurityUtils;
import com.sise.oj.util.StringUtils;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    private final SecurityProperties properties;
    private final RsaProperties rsaProperties;
    private final RedisUtils redisUtils;
    @Resource
    private LoginProperties loginProperties;

    @ApiOperation("登录授权")
    @PostMapping("/login")
    public ResultJson<Object> login(@Validated @RequestBody AuthUserDto authUser, HttpServletRequest request) throws Exception {
        // 获取密码
        String password = RsaUtils.decryptByPrivateKey(rsaProperties.getPrivateKey(), authUser.getPassword());
        // 查询验证码
        String code = (String) redisUtils.get(authUser.getUuid());
        // 清除验证码
        redisUtils.del(authUser.getUuid());
        if (StringUtils.isBlank(code)) {
            throw new BadRequestException("验证码不存在或已过期");
        }
        if (StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
            throw new BadRequestException("验证码错误");
        }
        // 用户密码认证令牌
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authUser.getUsername(), password);
        // 获取用户信息
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 设置到Spring Security上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成token
        String token = tokenProvider.createToken(authentication);
        // 获取用户
        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
        // 保存用户在线信息
        onlineUserService.save(jwtUserDto, token, request);
        // 返回 token 和用户信息
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
            put("token", properties.getTokenStartWith() + token);
            put("user", jwtUserDto);
        }};
        return ResultJson.success(authInfo);
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/info")
    public ResultJson<UserDetails> getUserInfo() {
        return ResultJson.success(SecurityUtils.getCurrentUser());
    }

    @ApiOperation("获取验证码")
    @AnonymousGetMapping("/code")
    public ResultJson<Object> getCode() {
        // 获取验证码生产类
        Captcha captcha = loginProperties.getCaptcha();
        // 生成存在 Redis 中的验证码的 key 值
        String uuid = properties.getCodeKey() + IdUtil.simpleUUID();
        String captchaValue = captcha.text();
        // 打印验证码
        System.out.println("验证码类型：" + captcha.getCharType() + " 验证码：" + captchaValue);
        // 保存验证码到 Redis 中，并设置过期时间（分钟）
        redisUtils.set(uuid, captchaValue, loginProperties.getLoginCode().getExpiration(), TimeUnit.MINUTES);
        // 验证码信息
        Map<String, Object> captchaImg = new HashMap<String, Object>(2) {{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return ResultJson.success(captchaImg);
    }

    @ApiOperation("退出登录")
    @DeleteMapping("/logout")
    public ResultJson<?> logout(HttpServletRequest request) {
        onlineUserService.logout(tokenProvider.getToken(request));
        return ResultJson.success(null);
    }
}
