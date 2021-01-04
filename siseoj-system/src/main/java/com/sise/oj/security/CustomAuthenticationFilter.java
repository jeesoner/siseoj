package com.sise.oj.security;

import com.alibaba.fastjson.JSONObject;
import com.sise.oj.domain.dto.AuthUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 支持表单登录和rest登录的认证过滤器
 * 这里处理登录请求
 *
 * @author Cijee
 * @version 1.0
 */
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 请求格式为json
        if (MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())) {
            UsernamePasswordAuthenticationToken authRequest;
            try (InputStream is = request.getInputStream()) {
                AuthUserDto user = JSONObject.parseObject(is, AuthUserDto.class);
                authRequest = new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                );
            } catch (IOException e) {
                log.error(e.getMessage());
                authRequest = new UsernamePasswordAuthenticationToken("", "");
            }
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }

        // 表单登录
        else {
            return super.attemptAuthentication(request, response);
        }
    }
}
