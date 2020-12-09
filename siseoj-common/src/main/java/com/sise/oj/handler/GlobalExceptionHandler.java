package com.sise.oj.handler;

import com.sise.oj.base.ResultJson;
import com.sise.oj.enums.ResultCode;
import com.sise.oj.utils.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author Cijee
 * @version 1.0
 */
@RestControllerAdvice(basePackages = "com.sise.oj.controller.*", annotations = {Controller.class, RestController.class})
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 10000 请求参数异常
     *
     * @param e 异常类
     * @return json
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResultJson<String> HandleParamException(MethodArgumentNotValidException e) {
        // 获取错误字段的数组
        //String[] str = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split("\\.");
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResultJson.failure(ResultCode.PARAM_ILLEGAL, message);
    }

    /**
     * 20000 用户登录异常
     *
     * @param e 异常类
     * @return json
     */
    //@ExceptionHandler(UserLoginFailedException.class)
    public ResultJson<String> HandleUserException(Exception e) {
        return ResultJson.failure(ResultCode.USER_LOGIN_ERROR);
    }

    /**
     * 30000 处理业务异常
     *
     * @param e 业务异常类
     * @return json
     */
    public ResultJson<String> HandleBusinessException(Exception e) {
        return ResultJson.failure(null);
    }

    /**
     * 40000 处理系统异常
     *
     * @param e 系统异常类
     * @return json
     */
    public ResultJson<String> HandleSystemException(Exception e) {
        return ResultJson.failure(null);
    }

    /**
     * 50000 处理数据异常
     *
     * @param e 数据异常类
     * @return json
     */
    public ResultJson<String> HandleDataException(Exception e) {
        return ResultJson.failure(null);
    }

    /**
     * 60000 处理接口异常
     *
     * @param e 接口异常类
     * @return json
     */
    public ResultJson<String> HandleInterfaceException(Exception e) {
        return ResultJson.failure(null);
    }

    /**
     * 70000 处理权限异常
     *
     * @param e 权限异常类
     * @return json
     */
    public ResultJson<String> HandlePermissionException(Exception e) {
        return ResultJson.failure(null);
    }

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Throwable.class)
    public ResultJson<?> handleException(Throwable e) {
        log.error(ThrowableUtil.getStackTrace(e));
        return ResultJson.failure(ResultCode.ERROR, e.getMessage());
    }
}
