package com.sise.oj.test;

import cn.hutool.core.util.IdUtil;
import com.sise.oj.domain.vo.EmailVo;
import com.sise.oj.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Cijee
 * @version 1.0
 */
@SpringBootTest
public class EmailTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void test() {
        String[] tos = new String[] {"saykuray@foxmail.com"};
        emailService.send(new EmailVo(tos, "测试", "测试"), emailService.find());
    }

    @Test
    public void sendResetTest() {
        emailService.sendRestPassword("Cijee", IdUtil.simpleUUID().substring(0, 21),
                "saykuray@foxmail.com");
    }

}
