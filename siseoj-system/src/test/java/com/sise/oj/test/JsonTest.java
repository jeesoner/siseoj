package com.sise.oj.test;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Cijee
 * @version 1.0
 */
@SpringBootTest
public class JsonTest {

    @Test
    public void test1() {
        Set<Long> ids = new HashSet<>();
        ids.add(1L);
        ids.add(5L);
        ids.add(7L);
        ids.add(0L);
        String json = JSONObject.toJSONString(ids);
        System.out.println(json);
    }
}
