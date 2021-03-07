package com.sise.oj.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * API 统一返回状态码
 *
 * @author Cijee
 * @version 1.0
 */
public enum ResultCode {


    /**
     * {@code 200 请求成功}.
     */
    SUCCESS(200, "请求成功"),

    /**
     * {@code 400 错误的请求}.
     */
    BAD_REQUEST(400, "错误的请求"),

    /**
     * {@code 401 请求要求用户的身份认证}.
     */
    UNAUTHORIZED(401, "请求要求用户的身份认证"),

    /**
     * {@code 403 拒绝请求，无权限访问}.
     */
    FORBIDDEN(403, "拒绝请求，无权限访问"),

    /**
     * {@code 404 请求的资源无法找到}.
     */
    NOT_FOUND(404, "请求的资源无法找到"),

    /**
     * {@code 405 不支持当前请求方法}.
     */
    METHOD_NOT_ALLOWED(405, "不支持当前请求方法"),

    /**
     * {@code 415 不支持当前媒体类型}.
     */
    UNSUPPORTED_MEDIA_TYPE(415, "不支持当前媒体类型"),

    /**
     * {@code 500 服务器内部错误}.
     */
    ERROR(500, "服务器内部错误");

    private final Integer code;

    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return code;
    }

    public String message() {
        return message;
    }

    /* 校验重复的code值 */
    public static void main(String[] args) {
        ResultCode[] ApiResultCodes = ResultCode.values();
        List<Integer> codeList = new ArrayList<>();
        for (ResultCode ApiResultCode : ApiResultCodes) {
            if (codeList.contains(ApiResultCode.code)) {
                System.out.println(ApiResultCode.code);
            } else {
                codeList.add(ApiResultCode.code);
            }
        }
    }
}
