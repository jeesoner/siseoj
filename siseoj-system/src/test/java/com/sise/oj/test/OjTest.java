package com.sise.oj.test;

import com.sise.oj.judge.client.OnlineJudgeHttpClient;
import com.sise.oj.judge.entity.JudgeResult;
import com.sise.oj.judge.entity.JudgeSubmitParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Cijee
 * @version 1.0
 */
@SpringBootTest
public class OjTest {

    @Autowired
    private OnlineJudgeHttpClient onlineJudgeHttpClient;

    @Test
    public void Test() throws InterruptedException {
        JudgeSubmitParam param = new JudgeSubmitParam();
        param.setType("result");
        param.setType("result");
        param.setTimeLimit(1000);
        param.setMemoryLimit(64);
        param.setRid(1L);
        param.setPid(1L);
        param.setCode("#include<iostream>\n using namespace std; int main() {int a, b; cin >> a >> b; cout << a + b << endl;}");
        param.setLanguage(1);

        boolean submit = onlineJudgeHttpClient.submit(param);
        System.out.println(submit);
        Thread.sleep(5000);
        JudgeResult result = onlineJudgeHttpClient.result(1L);
        System.out.println(result);
    }
}
