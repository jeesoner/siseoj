package com.sise.oj.test;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sise.oj.domain.ProblemCount;
import com.sise.oj.service.ProblemCountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Cijee
 * @version 1.0
 */
@SpringBootTest
public class LockTest {

    @Autowired
    private ProblemCountService problemCountService;

    /**
     * 测试并发情况下的更新
     */
    @Test
    public void versionTest() {
        // 更新problemCount pid = 4 的表
        System.out.println("======================");
        for (int i = 0; i < 1; i++) {
            System.out.println("???????????????????");
            new Thread(() -> {
                ProblemCount problemCount = problemCountService
                        .getOne(Wrappers.lambdaQuery(ProblemCount.class).eq(ProblemCount::getPid, 4));
                problemCount.setTotal(problemCount.getTotal() + 1);
                System.out.println("???????????????????");
                System.out.println(Thread.currentThread().getName() + ": id: " + problemCount.getTotal());
                problemCountService.updateById(problemCount);
            }).start();
        }
    }
}