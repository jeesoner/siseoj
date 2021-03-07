package com.sise.oj.security.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 已认证用户访问资源 处理器
 *
 * @author Cijee
 * @version 1.0
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    // 当用户在没有授权的情况下访问受保护的REST资源时，将调用此方法发送403 Forbidden响应
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "拒绝请求，无权限访问");
    }
}
