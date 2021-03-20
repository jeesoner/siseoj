package com.sise.oj.controller;

import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.User;
import com.sise.oj.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cijee
 * @version 1.0
 */
@RestController("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("更新用户信息")
    @PutMapping
    public ResultJson<String> update(@Validated(User.Update.class) @RequestBody User user) {
        userService.update(user);
        return ResultJson.success("更新用户信息成功");
    }
}
