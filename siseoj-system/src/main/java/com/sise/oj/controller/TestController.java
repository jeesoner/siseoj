package com.sise.oj.controller;

import com.sise.oj.service.ProblemCountService;
import com.sise.oj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    private final UserService userService;
    private final ProblemCountService problemCountService;

    public TestController(UserService userService, ProblemCountService problemCountService) {
        this.userService = userService;
        this.problemCountService = problemCountService;
    }
}
