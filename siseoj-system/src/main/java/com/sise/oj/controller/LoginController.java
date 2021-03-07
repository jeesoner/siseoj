package com.sise.oj.controller;

import com.sise.oj.base.ResultJson;
import com.sise.oj.enums.ResultCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CiJee
 * @version 1.0
 */
@RestController
public class LoginController {

    @GetMapping("/login")
    public ResultJson<?> login() {
        return ResultJson.failure(ResultCode.ERROR);
    }
}
