package com.sise.oj.security.filter;

import cn.hutool.core.util.StrUtil;
import com.sise.oj.security.TokenProvider;
import com.sise.oj.security.bean.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
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

    public TokenFilter(TokenProvider tokenProvider, SecurityProperties properties) {
        this.tokenProvider = tokenProvider;
        this.properties = properties;
    }

    /**
     * 拦截后处理逻辑
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(httpServletRequest);
        // 不为空的token不用检查Redis
        if (StrUtil.isNotBlank(token)) {
            log.info("获取了token");
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
