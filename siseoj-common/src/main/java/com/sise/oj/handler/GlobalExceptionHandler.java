package com.sise.oj.handler;

import com.sise.oj.base.Result;
import com.sise.oj.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
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
    public Result<String> HandleParamException(Exception e) {
        return Result.failure(ResultCode.INTERFACE_FORBID_VISIT);
    }

    /**
     * 20000 请求参数异常
     *
     * @param e 异常类
     * @return json
     */
    public Result<String> HandleUserException(Exception e) {
        return Result.failure(null);
    }

    /**
     * 30000 处理业务异常
     *
     * @param e 业务异常类
     * @return json
     */
    public Result<String> HandleBusinessException(Exception e) {
        return Result.failure(null);
    }

    /**
     * 40000 处理系统异常
     *
     * @param e 系统异常类
     * @return json
     */
    public Result<String> HandleSystemException(Exception e) {
        return Result.failure(null);
    }

    /**
     * 50000 处理数据异常
     *
     * @param e 数据异常类
     * @return json
     */
    public Result<String> HandleDataException(Exception e) {
        return Result.failure(null);
    }

    /**
     * 60000 处理接口异常
     *
     * @param e 接口异常类
     * @return json
     */
    public Result<String> HandleInterfaceException(Exception e) {
        return Result.failure(null);
    }

    /**
     * 70000 处理权限异常
     *
     * @param e 权限异常类
     * @return json
     */
    public Result<String> HandlePermissionException(Exception e) {
        return Result.failure(null);
    }
}
