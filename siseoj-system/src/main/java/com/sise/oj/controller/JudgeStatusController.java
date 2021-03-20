package com.sise.oj.controller;

import com.sise.oj.judge.client.OnlineJudgeHttpClient;
import com.sise.oj.service.JudgeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cijee
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "评测结果接口")
@RequestMapping("/judge")
public class JudgeStatusController {

    private final JudgeService judgeService;

    private final OnlineJudgeHttpClient onlineJudgeHttpClient;
}
