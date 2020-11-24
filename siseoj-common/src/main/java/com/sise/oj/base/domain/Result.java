package com.sise.oj.base.domain;

import com.sise.oj.base.enums.ResultCode;

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
        this.code = 200;
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

    private static<T> Result<T> success(T data) {
        return new Result<>();
    }

    public static void main(String[] args) {
        System.out.println(ResultCode.INTERFACE_ADDRESS_INVALID);
    }
}
