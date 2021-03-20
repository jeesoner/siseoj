package com.sise.oj.judge.client;

import com.sise.oj.exception.BusinessException;
import com.sise.oj.judge.entity.JudgeResult;
import com.sise.oj.judge.entity.JudgeSubmitParam;
import com.sise.oj.judge.entity.ServerResultDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author Cijee
 * @version 1.0
 */
@Component
public class OnlineJudgeHttpClient {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private ResponseParser responseParser;

    @Value("${oj.judge.url}")
    private String serverUrl;

    private final HttpHeaders headers = new HttpHeaders();

    /**
     * 初始化设置头部
     */
    public OnlineJudgeHttpClient() {
        // 设置头部
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    }

    /**
     * 提交到判题机
     *
     * @param param 请求参数
     * @return JudgeResultDto
     */
    public boolean submit(JudgeSubmitParam param) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("type", "submit");
        map.add("pid", param.getPid());
        map.add("rid", param.getRid());
        map.add("timelimit", param.getTimeLimit());
        map.add("memorylimit", param.getMemoryLimit());
        map.add("code", param.getCode());
        map.add("language", param.getLanguage());
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
        ServerResultDto body = responseParser.getResponseBody(restTemplate.postForEntity(serverUrl, request, ServerResultDto.class));
        if (!body.getStatus().equals(200)) {
            throw new BusinessException(body.getMsg());
        }
        return true;
    }

    /**
     * 获取评测结果
     *
     * @param rid 评测表主键
     * @return JudgeResultDto
     */
    public JudgeResult result(Long rid) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("type", "result");
        map.add("rid", rid);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
        ServerResultDto body = responseParser.getResponseBody(restTemplate.postForEntity(serverUrl, request, ServerResultDto.class));
        return body.getData();
    }
}
