package com.sise.oj.domain.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Cijee
 * @version 1.0
 */
@Data
public class ProblemQueryParam implements Serializable {

    /**
     * 题号
     */
    private Long pid;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 题目难度
     */
    private Integer difficulty;

    /**
     * 题目来源表主键
     */
    private Long sourceId;

    /**
     * 标签主键
     */
    private Long tid;
}
