package com.sise.oj.handler;

import com.sise.oj.base.ResultJson;
import com.sise.oj.enums.ResultCode;
import com.sise.oj.exception.BadConfigurationException;
import com.sise.oj.exception.BadRequestException;
import com.sise.oj.exception.DataExistException;
import com.sise.oj.exception.DataNotFoundException;
import com.sise.oj.util.ThrowableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * 全局异常处理
 *
 * @author Cijee
 * @version 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理接口参数验证异常
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResultJson<String> HandleParamException(MethodArgumentNotValidException e) {
        // 打印堆栈信息
        log.error(ThrowableUtils.getStackTrace(e));
        String[] str = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split("\\.");
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String msg = "不能为空";
        if(msg.equals(message)){
            message = str[1] + ":" + message;
        }
        return ResultJson.failure(ResultCode.PARAM_ERROR, message);
    }

    /**
     * 处理配置类错误异常
     */
    @ExceptionHandler(BadConfigurationException.class)
    public ResultJson<String> HandleBadConfigurationException(BadConfigurationException e) {
        return ResultJson.failure(ResultCode.SYSTEM_CONFIG_ERROR, e.getMessage());
    }

    /**
     * 处理数据不存在异常
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ResultJson<?> handleDataNotFoundException(DataNotFoundException e) {
        return ResultJson.failure(ResultCode.DATA_NOT_FOUND, e.getMessage());
    }

    /**
     * 处理数据已存在异常
     */
    @ExceptionHandler(DataExistException.class)
    public ResultJson<?> handleDataExistException(DataExistException e) {
        return ResultJson.failure(ResultCode.DATA_EXIST, e.getMessage());
    }

    /**
     * BadCredentialsException
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResultJson<String> badCredentialsException(BadCredentialsException e) {
        // 打印堆栈信息
        String message = "坏的凭证".equals(e.getMessage()) ? "用户名或密码不正确" : e.getMessage();
        log.error(message);
        return ResultJson.failure(ResultCode.USER_ERROR, message);
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = BadRequestException.class)
    public ResultJson<?> badRequestException(BadRequestException e) {
        // 打印堆栈信息
        log.error(ThrowableUtils.getStackTrace(e));
        return ResultJson.failure(e.getCode(), e.getMessage());
    }

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Throwable.class)
    public ResultJson<?> handleException(Throwable e) {
        String stackTrace = ThrowableUtils.getStackTrace(e);
        log.error(stackTrace);
        return ResultJson.failure(ResultCode.ERROR, e.getMessage());
    }
}
