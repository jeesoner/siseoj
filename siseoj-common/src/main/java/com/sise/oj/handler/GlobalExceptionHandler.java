package com.sise.oj.handler;

import com.sise.oj.base.ResultJson;
import com.sise.oj.enums.ResultCode;
import com.sise.oj.exception.*;
import com.sise.oj.util.ThrowableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Set;

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
     * 400 - Bad Request @Validated 校验错误异常处理
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultJson<String> HandleParamException(MethodArgumentNotValidException e) {
        // 打印堆栈信息
        log.error(ThrowableUtils.getStackTrace(e));
        String[] str = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split("\\.");
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String msg = "不能为空";
        if(msg.equals(message)){
            message = str[1] + ":" + message;
        }
        return ResultJson.failure(ResultCode.BAD_REQUEST, message);
    }

    /**
     * 400 - Bad Request 处理自定义BadRequestException 的异常！
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResultJson<?> handleBadRequestException(BadRequestException e) {
        // 打印堆栈信息
        log.error("错误的请求-------------->{}", e.getMessage());
        return ResultJson.failure(e.getCode(), e.getMessage());
    }

    /**
     * 400 - Bad Request 处理缺少请求参数
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultJson<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("缺少必要的请求参数-------------->{}", e.getMessage());
        return ResultJson.failure(ResultCode.BAD_REQUEST, "缺少必要的请求参数：" + e.getMessage());
    }

    /**
     * 400 - Bad Request 参数解析失败
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultJson<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("解析参数格式失败", e);
        return ResultJson.failure(ResultCode.BAD_REQUEST, "解析参数格式失败");
    }

    /**
     * 400 - Bad Request 参数绑定失败
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResultJson<String> handleBindException(BindException e) {
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        log.error(message);
        return ResultJson.failure(ResultCode.BAD_REQUEST, message);
    }

    /**
     * 400 - Bad Request 参数验证失败
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultJson<String> handleServiceException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        log.error("[参数验证失败]parameter:" + message);
        return ResultJson.failure(ResultCode.BAD_REQUEST, "[参数验证失败]parameter:" + message);
    }

    /**
     * 400 - Bad credentials 用户未认证
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadCredentialsException.class)
    public ResultJson<String> handleBadCredentialsException(BadCredentialsException e) {
        // 打印堆栈信息
        String message = "坏的凭证".equals(e.getMessage()) ? "用户名或密码不正确" : e.getMessage();
        log.error("unauthorized-------------->{}", e.getMessage());
        return ResultJson.failure(ResultCode.UNAUTHORIZED, message);
    }

    /**
     * 403 - forbidden 拒绝请求，无权限访问
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResultJson<String> handleAccessDeniedException(AccessDeniedException e){
        log.error("forbidden-------------->{}", e.getMessage());
        return ResultJson.failure(ResultCode.FORBIDDEN);
    }

    /**
     * 405 - Method Not Allowed 不支持当前请求方法
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultJson<String> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        log.error(ThrowableUtils.getStackTrace(e));
        return ResultJson.failure(ResultCode.METHOD_NOT_ALLOWED);
    }

    /**
     * 415 - Unsupported Media Type 不支持当前媒体类型
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResultJson<String> handleHttpMediaTypeNotSupportedException(Exception e) {
        log.error(ThrowableUtils.getStackTrace(e));
        return ResultJson.failure(ResultCode.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * 500 - internal_server_error 处理自定义 BusinessException 的异常！
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BusinessException.class)
    public ResultJson<String> handleServiceException(BadRequestException e) {
        // 打印堆栈信息
        log.error("业务逻辑异常", e);
        return ResultJson.failure(e.getCode(), e.getMessage());
    }

    /**
     * 500 - Bad Configuration 服务配置信息错误
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BadConfigurationException.class)
    public ResultJson<String> HandleBadConfigurationException(BadConfigurationException e) {
        log.error(ThrowableUtils.getStackTrace(e));
        return ResultJson.failure(ResultCode.ERROR, e.getMessage());
    }

    /**
     * 404 - Data not found 数据不存在
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    public ResultJson<String> handleDataNotFoundException(DataNotFoundException e) {
        log.error("数据不存在-------------->{}", e.getMessage());
        return ResultJson.failure(ResultCode.ERROR, e.getMessage());
    }

    /**
     * 500 - Data exist 数据已存在
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataExistException.class)
    public ResultJson<String> handleDataExistException(DataExistException e) {
        log.error("数据已存在-------------->{}", e.getMessage());
        return ResultJson.failure(ResultCode.ERROR, e.getMessage());
    }

    /**
     * 500 - Internal Server Error 操作数据库出现异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLException.class)
    public ResultJson<String> handleSQLException(SQLException e) {
        log.error("操作数据库出现异常", e);
        return ResultJson.failure(ResultCode.ERROR, "操作失败！错误提示：" + e.getMessage());
    }

    /**
     * 500 - Internal Server Error 系统通用异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ResultJson<String> handleException(Throwable e) {
        log.error(ThrowableUtils.getStackTrace(e));
        return ResultJson.failure(ResultCode.ERROR, e.getMessage());
    }
}
