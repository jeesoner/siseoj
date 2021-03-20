package com.sise.oj;

import com.sise.oj.annotation.rest.AnonymousGetMapping;
import com.sise.oj.util.SpringContextHolder;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@EnableAsync
@RestController
@Api(hidden = true)
@EnableTransactionManagement
@MapperScan(basePackages = "com.sise.oj.mapper")
@SpringBootApplication(exclude = {JacksonAutoConfiguration.class})
public class AppRun {

    public static void main(String[] args) {
        SpringApplication.run(AppRun.class, args);
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean(name = "crawlExecutorPool")
    public ExecutorService crawlExecutorPool() {
        // 获取Java虚拟机的可用的处理器数，最佳线程个数，处理器数*2。根据实际情况调整
        int curSystemThreads = Runtime.getRuntime().availableProcessors() * 2;
        log.info("------------系统可用线程池线程个数：{}------------", curSystemThreads);
        // 创建线程池
        return Executors.newFixedThreadPool(curSystemThreads);
    }

    /**
     * 访问首页提示
     *
     * @return -
     */
    @AnonymousGetMapping("/")
    public String index() {
        return "Backend service started successfully";
    }
}
