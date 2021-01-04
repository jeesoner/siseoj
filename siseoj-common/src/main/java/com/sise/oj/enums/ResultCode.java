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
    SUCCESS(0, "成功"),

    ERROR(-1, "未知异常"),

    /* 参数错误：10000-19999 */
    PARAM_ERROR(10000, "请求参数异常"),
    PARAM_IS_INVALID(10001, "请求参数无效"),
    PARAM_IS_BLANK(10002, "请求参数为空"),
    PARAM_TYPE_BIND_ERROR(10003, "请求参数类型错误"),
    PARAM_NOT_COMPLETE(10004, "请求参数缺失"),

    /* 用户错误：20000-29999*/
    USER_ERROR(20000, "用户异常"),
    USER_NOT_LOGGED_IN(20001, "用户未登录"),
    USER_LOGIN_ERROR(20002, "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(20003, "账号已被禁用"),
    USER_NOT_EXIST(20004, "用户不存在"),
    USER_HAS_EXISTED(20005, "用户已存在"),

    /* 业务错误：30000-39999 */
    BUSINESS_ERROR(30000, "业务异常"),
    BUSINESS_QUESTIONED_USER_NOT_EXIST(30001, "某业务出现问题"),

    /* 系统错误：40000-49999 */
    SYSTEM_ERROR(40000, "系统异常"),
    SYSTEM_INNER_ERROR(40001, "系统繁忙，请稍后重试"),
    SYSTEM_CONFIG_ERROR(40002, "系统配置信息错误"),

    /* 数据错误：50000-59999 */
    DATA_NOT_FOUND(50001, "数据不存在"),
    DATA_EXIST(50003, "数据已存在"),

    /* 接口错误：60000-69999 */
    INTERFACE_ERROR(60000, "接口异常"),
    INTERFACE_INNER_INVOKE_ERROR(60001, "内部系统接口调用异常"),
    INTERFACE_OUTER_INVOKE_ERROR(60002, "外部系统接口调用异常"),
    INTERFACE_FORBID_VISIT(60003, "该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID(60004, "接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT(60005, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(60006, "接口负载过高"),

    /* 权限错误：70000-79999 */
    PERMISSION_NO_ACCESS(70001, "无访问权限");

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
