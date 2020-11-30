package com.sise.oj.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cijee
 * @version 1.0
 */
@Slf4j
public class IPAddressUtils {

    public static String getClientIPAddress(HttpServletRequest request) {
        // 获取HTTP代理字段
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            // Apache http服务器 请求字段
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            // Apache http服务器 请求字段
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(",")) {
            // 可能有多个IP以,分隔，一般取第一个IP
            return ip.split(",")[0];
        } else {
            return ip;
        }
    }

}
