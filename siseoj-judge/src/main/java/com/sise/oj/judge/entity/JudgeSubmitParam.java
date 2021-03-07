package com.sise.oj.judge.entity;

import lombok.Data;

/**
 * 提交到判题机的参数
 *
 * @author Cijee
 * @version 1.0
 */
@Data
public class JudgeSubmitParam {

    private String type;

    private Long pid;

    /**
     * 评测表的主键
     */
    private Long rid;

    private Integer timeLimit;

    private Integer memoryLimit;

    private String code;

    private Integer language;
}
