package com.sise.oj.controller;

import com.sise.oj.base.ResultJson;
import com.sise.oj.service.UserInfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cijee
 * @version 1.0
 */
@RestController("/user")
public class UserController {

    private final UserInfoService userInfoService;

    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/login")
    public ResultJson<String> userLogin() {
        return ResultJson.success(null);
    }

}
