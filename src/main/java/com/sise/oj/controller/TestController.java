package com.sise.oj.controller;

import com.sise.oj.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
    public String get(@PathVariable Long id) {
        return userInfoService.getUserInfo(id).toString();
    }

}
