package com.sise.oj.controller;

import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.User;
import com.sise.oj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    private final UserService userService;

    public TestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping
    public String oj() {
        return "hello oj";
    }

    @PostMapping("/user")
    public ResultJson<String> register(@Valid @RequestBody User user) {
        System.out.println(user.getUsername());
        return ResultJson.success("成功");
    }

    @GetMapping("/{id}")
    public ResultJson<User> get(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResultJson.success(user);
    }

    @PostMapping("/post")
    public String testPost() {
        return "post";
    }
}
