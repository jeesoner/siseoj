package com.sise.oj.util;

import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author Cijee
 * @version 1.0
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 判断字符串是否为整数
     * @param str 字符串
     * @return true or false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
