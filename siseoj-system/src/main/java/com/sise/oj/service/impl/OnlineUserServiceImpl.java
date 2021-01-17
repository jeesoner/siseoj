package com.sise.oj.service.impl;

import com.sise.oj.domain.dto.JwtUserDto;
import com.sise.oj.domain.dto.OnlineUserDto;
import com.sise.oj.security.bean.SecurityProperties;
import com.sise.oj.service.OnlineUserService;
import com.sise.oj.util.ClientUtils;
import com.sise.oj.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author Cijee
 * @version 1.0
 */
@Slf4j
@Service
public class OnlineUserServiceImpl implements OnlineUserService {

    private final RedisUtils redisUtils;
    private final SecurityProperties properties;

    public OnlineUserServiceImpl(RedisUtils redisUtils, SecurityProperties properties) {
        this.redisUtils = redisUtils;
        this.properties = properties;
    }

    /**
     * 保存在线用户信息
     *
     * @param jwtUserDto 授权用户信息 dto
     * @param token token
     * @param request 请求
     */
    @Override
    public void save(JwtUserDto jwtUserDto, String token, HttpServletRequest request) {
        String ip = ClientUtils.getClientIPAddress(request);
        String browser = ClientUtils.getBrowser(request);
        OnlineUserDto onlineUserDto = null;
        // 构造在线用户信息dto
        try {
            onlineUserDto = new OnlineUserDto(
                    jwtUserDto.getUsername(),
                    jwtUserDto.getUser().getNickname(),
                    browser,
                    ip,
                    null,
                    token,
                    new Date()
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        redisUtils.set(properties.getOnlineKey() + token, onlineUserDto,
                // 过期时间(秒)
                properties.getTokenValidityInSeconds() / 1000);
    }

    /**
     * 退出登录
     *
     * @param token token
     */
    @Override
    public void logout(String token) {
        redisUtils.del(properties.getOnlineKey() + token);
    }

    @Override
    public OnlineUserDto getOne(String key) {
        return (OnlineUserDto) redisUtils.get(key);
    }
}
