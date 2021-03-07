package com.sise.oj.judge.client;

import com.sise.oj.domain.ProblemCase;
import com.sise.oj.judge.entity.ServerResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cijee
 * @version 1.0
 */
@Component
public class FileSystemHttpClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ResponseParser responseParser;

    @Value("${oj.fileServer.url}")
    private String fileServerUrl;

    /**
     * 上传评测样例
     *
     * @param pid 题号
     * @param list 样例列表
     * @return 成功 / 失败
     */
    public boolean submitJudgeCase(Long pid, List<ProblemCase> list) {
        Map<String, Object> map = new HashMap<String, Object>(3) {{
            put("type", "data");
            put("pid", pid);
            put("data", list);
        }};
        ResponseEntity<ServerResultDto> response = restTemplate.postForEntity(fileServerUrl, map, ServerResultDto.class);
        return responseParser.success(response);
    }

    /**
     * 上传特判程序
     *
     * @param pid 题号
     * @param code 特判程序
     * @return 成功 / 失败
     */
    public boolean submitSpj(Long pid, String code) {
        Map<String, Object> map = new HashMap<String, Object>(3) {{
            put("type", "spj");
            put("pid", pid);
            put("data", code);
        }};
        ResponseEntity<ServerResultDto> response = restTemplate.postForEntity(fileServerUrl, map, ServerResultDto.class);
        return responseParser.success(response);
    }
}
