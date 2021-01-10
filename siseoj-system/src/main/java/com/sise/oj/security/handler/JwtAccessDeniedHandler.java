package com.sise.oj.security.handler;

import com.sise.oj.base.ResultJson;
import com.sise.oj.enums.ResultCode;
import com.sise.oj.util.JsonUtils;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 无权限访问处理类
 *
 * @author Cijee
 * @version 1.0
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        // 设置响应类型为JSON
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        // 封装响应内容
        ResultJson<String> failure = ResultJson.failure(ResultCode.USER_ERROR, e.getMessage());
        httpServletResponse.getWriter().write(JsonUtils.objToJson(failure));
    }
}
