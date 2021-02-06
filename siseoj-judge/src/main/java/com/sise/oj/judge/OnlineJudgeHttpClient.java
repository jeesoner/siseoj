package com.sise.oj.judge;

import com.alibaba.fastjson.JSONObject;
import com.sise.oj.judge.param.JudgeSubmitParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private OnlineJudgeResponseParser responseParser;

    @Value("${oj.judge.url}")
    private String judgePath;

    private final HttpHeaders headers = new HttpHeaders();

    /**
     * 初始化设置头部
     */
    public OnlineJudgeHttpClient() {
        // 设置头部
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    }

    /**
     * 提交到评测机
     *
     * @param param 请求参数
     * @return json
     */
    public JSONObject submit(JudgeSubmitParam param) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("type", "submit");
        map.add("pid", param.getPid().toString());
        map.add("rid", param.getRid().toString());
        map.add("timelimit", param.getTimeLimit().toString());
        map.add("memorylimit", param.getMemoryLimit().toString());
        map.add("code", param.getCode());
        map.add("language", param.getLanguage().toString());
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> responseEntity = doPost(judgePath, request);
        return responseParser.parseBodyAsJsonObject(responseEntity);
    }

    public JSONObject getJudgeResult(Long rid) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("type", "getResult");
        map.add("rid", rid.toString());
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> responseEntity = doPost(judgePath, request);
        return responseParser.parseBodyAsJsonObject(responseEntity);
    }

    /**
     * 执行 post 请求
     *
     * @param url     -
     * @param request -
     * @return -
     */
    protected ResponseEntity<String> doPost(String url, HttpEntity<MultiValueMap<String, Object>> request) {
        return restTemplate.postForEntity(url, request, String.class);
    }

    /**
     * 执行 post 请求，返回Resource
     *
     * @param url     -
     * @param request -
     * @return -
     */
    protected ResponseEntity<org.springframework.core.io.Resource> doPostAsResource(String url, HttpEntity<MultiValueMap<String, Object>> request) {
        return restTemplate.postForEntity(url, request, org.springframework.core.io.Resource.class);
    }

    /**
     * 执行 get 请求
     *
     * @param url     -
     * @param request -
     * @return -
     */
    protected ResponseEntity<String> doGet(String url, HttpEntity<MultiValueMap<String, Object>> request) {
        return restTemplate.getForEntity(url, String.class);
    }
}
