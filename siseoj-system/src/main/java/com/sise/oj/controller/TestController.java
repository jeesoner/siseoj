package com.sise.oj.controller;

import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.User;
import com.sise.oj.domain.UserAuth;
import com.sise.oj.service.UserAuthService;
import com.sise.oj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    private final UserService userInfoService;

    private final UserAuthService userAuthService;

    public TestController(UserService userInfoService, UserAuthService userAuthService) {
        this.userInfoService = userInfoService;
        this.userAuthService = userAuthService;
    }

    @RequestMapping
    public String oj() {
        return "hello oj";
    }

    @GetMapping("/{id}")
    public ResultJson<User> get(@PathVariable Long id) {
        return ResultJson.success(userInfoService.getUserInfo(id));
    }

    @PostMapping("/user")
    public ResultJson<String> register(@Valid @RequestBody User user) {
        System.out.println(user.getUsername());
        return ResultJson.success("成功");
    }

    @PostMapping("/post")
    public String testPost() {
        return "post";
    }

    @GetMapping("/auth")
    public UserAuth testAuth() {
        return userAuthService.findByName("root");
    }
}
