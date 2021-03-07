package com.sise.oj.judge.client;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * 判题机返回数据解析器
 *
 * @author Cijee
 * @version 1.0
 */
@Component
public class ResponseParser {

    /**
     * 提取response body
     * 如果返回码为200则提取body
     * 否则抛出异常
     *
     * @param response 服务器响应信息
     * @return json
     */
    protected <T> T getResponseBody(ResponseEntity<T> response) throws RuntimeException {
        if (HttpStatus.OK.value() == response.getStatusCodeValue()) {
            return response.getBody();
        } else {
            throw new RuntimeException("请求返回码不正常,status=" + response.getStatusCode());
        }
    }

    /**
     * 返回请求是否成功
     *
     * @param response 服务器响应信息
     * @return boolean
     */
    protected <T> boolean success(ResponseEntity<T> response) {
        return HttpStatus.OK.value() == response.getStatusCodeValue();
    }
}