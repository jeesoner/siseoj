package com.sise.oj.security.handler;

import com.alibaba.fastjson.JSON;
import com.sise.oj.base.ResultJson;
import com.sise.oj.enums.ResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author CiJee
 * @version 1.0
 */
@Component
public class FailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        ResultJson<?> json = ResultJson.failure(ResultCode.USER_NOT_LOGGED_IN);
        httpServletResponse.getWriter().write(JSON.toJSONString(json));
    }
}
