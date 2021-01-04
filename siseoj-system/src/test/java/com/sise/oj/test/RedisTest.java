package com.sise.oj.test;

import com.sise.oj.util.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Cijee
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void pingRedisTest() {
        //redisUtils.set("aaa", "hello");
        Object aaa = redisUtils.get("aaa");
        System.out.println(aaa);
    }

}
