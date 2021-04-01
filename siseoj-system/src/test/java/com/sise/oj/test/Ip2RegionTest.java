package com.sise.oj.test;

import com.sise.oj.util.ClientUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Cijee
 * @version 1.0
 */
@SpringBootTest
public class Ip2RegionTest {

    @Test
    public void test() {
        System.out.println(ClientUtils.getCityInfo("39.189.30.40"));
    }
}
