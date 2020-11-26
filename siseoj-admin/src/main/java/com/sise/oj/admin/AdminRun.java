package com.sise.oj.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sise.oj.mapper")
public class AdminRun {

    public static void main(String[] args) {
        SpringApplication.run(AdminRun.class, args);
    }

}
