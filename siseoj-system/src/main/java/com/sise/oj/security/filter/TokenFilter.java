package com.sise.oj.security.filter;

import cn.hutool.core.util.StrUtil;
import com.sise.oj.domain.dto.OnlineUserDto;
import com.sise.oj.security.TokenProvider;
import com.sise.oj.security.bean.SecurityProperties;
import com.sise.oj.service.OnlineUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token 拦截器
 * 处理Token
 *
 * @author Cijee
 * @version 1.0
 */
@Slf4j
public class TokenFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final SecurityProperties properties;
    private final OnlineUserService onlineUserService;

    public TokenFilter(TokenProvider tokenProvider, SecurityProperties properties, OnlineUserService onlineUserService) {
        this.tokenProvider = tokenProvider;
        this.properties = properties;
        this.onlineUserService = onlineUserService;
    }

    /**
     * 拦截后处理逻辑
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(httpServletRequest);
        // 请求带 token 的需要去 redis 中检查
        if (StrUtil.isNotBlank(token)) {
            OnlineUserDto onlineUserDto = null;
            try {
                onlineUserDto = onlineUserService.getOne(properties.getOnlineKey() + token);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            // redis中存在用户信息
            if (onlineUserDto != null && StringUtils.hasText(token)) {
                // 获取鉴权信息
                Authentication authentication = tokenProvider.getAuthentication(token);
                // 设置到上下文中
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // token 续期
                tokenProvider.checkRenew(token);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * 解析Token
     * @param request -
     * @return -
     */
    private String resolveToken(HttpServletRequest request) {
        // 获取请求头里的token
        String bearerToken = request.getHeader(properties.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(properties.getTokenStartWith())) {
            // 去掉令牌前缀
            return bearerToken.replace(properties.getTokenStartWith(), "");
        } else {
            log.error("非法Token:{}", bearerToken);
        }
        return null;
    }
}
