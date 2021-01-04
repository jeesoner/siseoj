package com.sise.oj.controller;

import com.sise.oj.base.ResultJson;
import com.sise.oj.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cijee
 * @version 1.0
 */
@RestController("/user")
public class UserController {

    private final UserService userInfoService;

    public UserController(UserService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/login")
    public ResultJson<String> userLogin() {
        return ResultJson.success(null);
    }

}
