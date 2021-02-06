package com.sise.oj.judge;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * 评测机返回数据解析器
 *
 * @author Cijee
 * @version 1.0
 */
@Component
public class OnlineJudgeResponseParser {
    /**
     * 解析response body 为JSONObject
     *
     * @param response 评测机返回数据
     * @return json
     */
    protected JSONObject parseBodyAsJsonObject(ResponseEntity<String> response) {
        return JSONObject.parseObject(getResponseBody(response));
    }

    /**
     * 提取response body
     * 如果返回码为200则提取body
     * 否则抛出异常
     *
     * @param response 评测机返回数据
     * @return json
     */
    protected String getResponseBody(ResponseEntity<String> response) throws RuntimeException {
        if (HttpStatus.OK.value() == response.getStatusCodeValue()) {
            return response.getBody();
        } else {
            throw new RuntimeException("请求的网页返回码不正常");
        }
    }
}