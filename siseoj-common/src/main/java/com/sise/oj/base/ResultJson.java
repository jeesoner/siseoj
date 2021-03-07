package com.sise.oj.base;

import com.sise.oj.enums.ResultCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 返回客户端的JSON封装类
 *
 * @author Cijee
 * @version 1.0
 */
@Getter
@Setter
public class ResultJson<T> implements Serializable {

    /* 是否响应成功 */
    private Boolean success;

    /* 响应状态码 */
    private Integer status;

    /* 日期 */
    private Date timestamp;

    /* 错误信息 */
    private String message;

    /* 响应数据 */
    private T data;

    /**
     * 成功
     *
     * @param data 返回结果
     */
    private ResultJson(T data) {
        this.success = true;
        this.status = ResultCode.SUCCESS.code();
        this.data = data;
        this.timestamp = new Date();
    }

    /**
     * 失败
     *
     * @param status 状态码
     * @param message 错误信息
     */
    private ResultJson(Integer status, String message) {
        this.success = false;
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }

    @Override
    public String toString() {
        return "ResultJson{" +
                "success=" + success +
                ", status=" + status +
                ", timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", data=[日志中不能查看]" +
                '}';
    }

    /**
     * 成功的结果
     *
     * @param data 返回结果
     * @param <T> 返回结果类型
     */
    public static<T> ResultJson<T> success(T data) {
        return new ResultJson<>(data);
    }

    /**
     * 失败的结果
     *
     * @param resultCode 状态码
     * @param <T> 返回结果类型
     */
    public static<T> ResultJson<T> failure(ResultCode resultCode) {
        return new ResultJson<>(resultCode.code(), resultCode.message());
    }

    /**
     * 失败的结果
     *
     * @param resultCode 状态码
     * @param message 错误信息
     * @param <T> 返回结果类型
     */
    public static<T> ResultJson<T> failure(ResultCode resultCode, String message) {
        return new ResultJson<>(resultCode.code(), message);
    }

    /**
     * 失败的结果
     *
     * @param status 状态码
     * @param message 错误信息
     * @param <T> 返回结果类型
     */
    public static<T> ResultJson<T> failure(Integer status, String message) {
        return new ResultJson<>(status, message);
    }
}
