package com.sise.oj.test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Cijee
 * @version 1.0
 */
public class SetTest {

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        Set<String> set1 = new HashSet<String>() {{
            add("aa");
            add("bb");
            add("cc");
            add(null);
        }};
        Set<String> set2 = new HashSet<String>() {{
        }};
        Set<String> result = new HashSet<>();
        result.addAll(set1);
        result.removeAll(set2);
        long time2 = System.currentTimeMillis();
        System.out.println("差集" + result + (time2 - time));

        result.clear();
        result.addAll(set2);
        result.removeAll(set1);
        System.out.println("差集" + result);

    }

}
