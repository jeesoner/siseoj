package com.sise.oj.test;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.sise.oj.base.SysConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Cijee
 * @version 1.0
 */
public class SetTest {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sd = new SimpleDateFormat(SysConstants.TIME_PATTERN);
        String a1 = "2020-03-15 15:00:00", a2 = "2020-03-15 19:30:00";
        Date date1 = sd.parse(a1);
        Date date2 = sd.parse(a2);
        System.out.println(DateUtil.between(date1, date2, DateUnit.HOUR));
    }

}
