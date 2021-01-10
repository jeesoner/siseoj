package com.sise.oj.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sise.oj.enums.ResultCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 返回客户端的JSON封装类
 *
 * @author Cijee
 * @version 1.0
 */
public class ResultJson<T> implements Serializable {

    /* 是否响应成功 */
    private Boolean success;

    /* 响应状态码 */
    private Integer code;

    /* 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;

    /* 错误信息 */
    private String message;

    /* 响应数据 */
    private T data;

    /* 构造器私有，不允许外界自己创建返回类 */
    private ResultJson() {
        this.timestamp = new Date();
        this.code = ResultCode.SUCCESS.code();
        this.success = true;
    }

    /**
     * 请求成功
     *
     * @param data 数据
     */
    private ResultJson(T data) {
        this();
        this.data = data;
    }

    /**
     * 使用状态码枚举类的错误信息
     *
     * @param resultCode 状态码
     */
    private ResultJson(ResultCode resultCode) {
        this.timestamp = new Date();
        this.code = resultCode.code();
        this.success = false;
        this.message = resultCode.message();
    }

    /**
     * 自定义错误信息
     *
     * @param resultCode 状态码
     * @param message 错误信息
     */
    private ResultJson(ResultCode resultCode, String message) {
        this.timestamp = new Date();
        this.code = resultCode.code();
        this.success = false;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ResultJson{" +
                "success=" + success +
                ", code=" + code +
                ", timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", data=[日志中不能查看]" +
                '}';
    }

    public static<T> ResultJson<T> success(T data) {
        return new ResultJson<>(data);
    }

    public static<T> ResultJson<T> failure(ResultCode resultCode) {
        return new ResultJson<>(resultCode);
    }

    public static<T> ResultJson<T> failure(ResultCode resultCode, String message) {
        return new ResultJson<>(resultCode, message);
    }

}
