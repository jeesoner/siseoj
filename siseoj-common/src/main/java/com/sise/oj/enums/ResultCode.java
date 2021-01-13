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

    /* 成功状态码 */
    SUCCESS(0, "请求成功"),

    ERROR(-1, "未知异常"),

    /* 参数错误：10000-19999 */
    PARAM_ERROR(10000, "请求参数异常"),

    /* 用户错误：20000-29999*/
    USER_ERROR(20000, "用户异常"),

    /* 业务错误：30000-39999 */
    BUSINESS_ERROR(30000, "业务异常"),

    /* 系统错误：40000-49999 */
    SYSTEM_ERROR(40000, "服务器错误"),
    SYSTEM_CONFIG_ERROR(40001, "服务器配置信息错误"),

    /* 数据错误：50000-59999 */
    DATA_NOT_FOUND(50001, "数据不存在"),
    DATA_EXIST(50002, "数据已存在"),

    /* 接口错误：60000-69999 */
    INTERFACE_ERROR(60000, "接口异常"),

    /* 权限错误：70000-79999 */
    INVALID_CREDENTIALS(70001, "凭据失效，请重新登录"),
    NO_PERMISSION(70002, "无权限访问");

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
