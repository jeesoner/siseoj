package com.sise.oj.judge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 提交到判题机的参数
 *
 * @author Cijee
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
