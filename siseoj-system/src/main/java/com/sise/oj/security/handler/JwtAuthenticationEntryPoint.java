package com.sise.oj.security.handler;

import com.sise.oj.base.ResultJson;
import com.sise.oj.enums.ResultCode;
import com.sise.oj.util.JsonUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理类
 *
 * @author Cijee
 * @version 1.0
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        // 设置响应类型为JSON
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        // 封装响应内容
        ResultJson<String> failure = ResultJson.failure(ResultCode.INVALID_CREDENTIALS, e == null ? "Unauthorized" : e.getMessage());
        httpServletResponse.getWriter().write(JsonUtils.objToJson(failure));
    }
}
