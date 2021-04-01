package com.sise.oj.base;

/**
 * 系统常量类
 *
 * @author Cijee
 * @version 1.0
 */
public class SysConstants {

    public static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final String WIN = "win";

    public static final String OS = "os";

    /**
     * 用于IP定位转换
     */
    public static final String REGION = "内网IP|内网IP";

    public static class Url {
        // IP归属地查询
        public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp?ip=%s&json=true";
    }
}
