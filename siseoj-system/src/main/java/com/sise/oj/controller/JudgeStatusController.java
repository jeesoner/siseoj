package com.sise.oj.controller;

import com.sise.oj.base.ResultJson;
import com.sise.oj.domain.Judge;
import com.sise.oj.domain.dto.JudgeDto;
import com.sise.oj.enums.ResultCode;
import com.sise.oj.judge.client.OnlineJudgeHttpClient;
import com.sise.oj.judge.entity.ServerResultDto;
import com.sise.oj.judge.entity.JudgeSubmitParam;
import com.sise.oj.service.JudgeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @ApiOperation("判题")
    @PostMapping("/submit")
    public ResultJson<Object> submit(@Validated @RequestBody JudgeDto judgeDto) {
        // 目前支持语言 C/C++
        Judge judge = judgeService.create(judgeDto);
        // 拿到插入的主键
        Long id = judge.getId();
        // 封装提交到评测机的参数
        JudgeSubmitParam param = new JudgeSubmitParam();
        // 设置评测表主键
        param.setRid(id);
        param.setPid(judge.getPid());
        param.setCode(judge.getCode());
        param.setLanguage(judge.getLanguage());
        param.setType("submit");
        // 设置时间限制
        param.setTimeLimit(judgeDto.getTimeLimit());
        // 设置空间限制
        param.setMemoryLimit(judgeDto.getMemoryLimit());
        ServerResultDto serverResultDto;
        try {
            serverResultDto = onlineJudgeHttpClient.submit(param);
        } catch (Exception e) {
            // 请求评测机出现异常，返回失败状态
            judgeService.error(judge);
            return ResultJson.failure(ResultCode.ERROR, "提交代码到本地评测机失败！评测机连接异常");
        }
        // 获取评判结果
        return ResultJson.success(null);
    }
}
