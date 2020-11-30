package com.sise.oj.controller;

import com.sise.oj.service.UserInfoService;
import com.sise.oj.base.Result;
import com.sise.oj.domain.po.UserInfoPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api")
public class TestController {

    private final UserInfoService userInfoService;

    public TestController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @RequestMapping("/oj")
    public String oj() {
        return "hello oj";
    }

    @GetMapping("/{id}")
    public Result<UserInfoPO> get(@PathVariable Long id) {
        return Result.success(userInfoService.getUserInfo(id));
    }
}
