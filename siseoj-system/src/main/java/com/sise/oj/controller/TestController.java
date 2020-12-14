package com.sise.oj.controller;

import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.UserInfo;
import com.sise.oj.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    private final UserInfoService userInfoService;

    public TestController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @RequestMapping
    public String oj() {
        return "hello oj";
    }

    @GetMapping("/{id}")
    public ResultJson<UserInfo> get(@PathVariable Long id) {
        return ResultJson.success(userInfoService.getUserInfo(id));
    }

    @PostMapping("/user")
    public ResultJson<String> register(@Valid @RequestBody UserInfo userInfo) {
        System.out.println(userInfo.getUsername());
        return ResultJson.success("成功");
    }

}
