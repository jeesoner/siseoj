package com.sise.oj.domain.base;

import com.sise.oj.enums.ResultCode;

import java.io.Serializable;

public class Result<T> implements Serializable {

    /* 是否响应成功 */
    private Boolean success;

    /* 响应状态码 */
    private Integer code;

    /* 响应数据 */
    private T data;

    /* 错误信息 */
    private String message;

    private Result() {
        this.code = ResultCode.SUCCESS.code();
        this.success = true;
    }

    private Result(T data) {
        this.code = 200;
        this.data = data;
        this.success = true;
    }

    private Result(ResultCode resultCode) {
        this.code = resultCode.code();
        this.success = false;
        this.message = resultCode.message();
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

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", code=" + code +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }

    public static<T> Result<T> success(T data) {
        return new Result<>(data);
    }

    public static<T> Result<T> failure(ResultCode resultCode) {
        return new Result<>(resultCode);
    }

}
