package com.sise.oj.controller;

import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.EmailConfig;
import com.sise.oj.domain.vo.EmailVo;
import com.sise.oj.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
@Api(tags = "工具：邮件管理")
public class EmailController {

    private final EmailService emailService;

    @GetMapping
    public ResultJson<EmailConfig> queryConfig(){
        return ResultJson.success(emailService.find());
    }

    @PutMapping
    @ApiOperation("配置邮件")
    public ResultJson<Object> updateConfig(@Validated @RequestBody EmailConfig emailConfig) throws Exception {
        emailService.config(emailConfig, emailService.find());
        return ResultJson.success(null);
    }

    @PostMapping
    @ApiOperation("发送邮件")
    public ResultJson<Object> sendEmail(@Validated @RequestBody EmailVo emailVo){
        emailService.send(emailVo, emailService.find());
        return ResultJson.success(null);
    }
}