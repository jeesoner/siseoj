package com.sise.oj.test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
public class SetTest {

    public static void main(String[] args) throws ParseException {
        /*SimpleDateFormat sd = new SimpleDateFormat(SysConstants.TIME_PATTERN);
        String a1 = "2020-03-15 15:00:00", a2 = "2020-03-15 19:30:00";
        Date date1 = sd.parse(a1);
        Date date2 = sd.parse(a2);
        System.out.println(DateUtil.between(date1, date2, DateUnit.HOUR));*/
        List<String> list = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h");
        System.out.println(list.subList(0, 3));
        System.out.println(list.subList(1, 10));
        System.out.println(list.subList(2, 10));
    }

}
