package com.sise.oj.judge.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 评测结果信息
 *
 * @author Cijee
 * @version 1.0
 */
@Data
public class ResultInfo implements Serializable {

    private String type;
    private List<CaseResult> caseResults;
    private String msg;
}
