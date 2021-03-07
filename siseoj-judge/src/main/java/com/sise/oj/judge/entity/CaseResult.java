package com.sise.oj.judge.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 样例评测结果
 *
 * @author Cijee
 * @version 1.0
 */
@Data
public class CaseResult implements Serializable {

    @JSONField(name = "case")
    private String caseId;
    private String result;
    private Long time;
    private Long memory;
    private Long runtime;
    private Integer score;
}
