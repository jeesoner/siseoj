package com.sise.oj.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author Cijee [2020/11/26]
 * @version 1.0
 */
@Slf4j
public class DateUtil {

    /**
     * 获取当前时间 yyyy-MM-dd HH:mm:ss
     *
     * @return 2020-11-26 15:39:00
     */
    public static String getCurrentTime() {
        // 大写的HH是24小时制，小写的hh是12小时制
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    /**
     * 获取当前日期 yyyy-MM-dd
     *
     * @return 2020-11-26
     */
    public static String getCurrentDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    /**
     * 获取系统当前时间戳，精确到毫秒
     *
     * @return 1606376624601
     */
    public static String getSystemTime() {
        // 13位时间戳精确到毫秒，10位时间戳精确到秒
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 得到两个日期的时间差
     *
     * @param start 开始时间 2020-01-10 00:00:00
     * @param end   结束时间 2020-10-10 00:00:00
     * @return      5270400000
     */
    public static long dateSubtraction(String start, String end) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date startDate = df.parse(start);
            Date endDate = df.parse(end);
            return endDate.getTime() - startDate.getTime();
        } catch (ParseException e) {
            log.error("日期转换错误", e);
            return 0;
        }
    }

    /**
     * 获取当前日期是一个星期的第几天
     *
     * @return [0-6] 表示周日到周六
     */
    public static int getDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 将日期转换成指定格式的字符串
     *
     * @param date 日期
     * @param pattern 格式 [yyyy-MM-dd HH:mm:ss]
     * @return 2020-11-26 15:39:00
     */
    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
}
