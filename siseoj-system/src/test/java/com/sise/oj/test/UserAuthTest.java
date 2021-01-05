package com.sise.oj.test;

import com.sise.oj.domain.UserAuth;
import com.sise.oj.mapper.UserAuthMapper;
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
public class UserAuthTest {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Test
    public void selectTest() {
        UserAuth userAuth = userAuthMapper.selectByName("root");
        System.out.println(userAuth.getUsername());
    }

}
