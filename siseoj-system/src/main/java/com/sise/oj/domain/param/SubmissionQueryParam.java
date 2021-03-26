package com.sise.oj.domain.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Cijee
 * @version 1.0
 */
@Data
public class SubmissionQueryParam implements Serializable {

    /**
     * 用户名
     */
    private String username;

    /**
     * 题目ID / displayId
     */
    private Long pid;

    /**
     * 评测状态
     */
    private Integer status;

    /**
     * 语言
     */
    private Integer language;

    /**
     * 是否只查询自己
     */
    private Boolean mine;

    /**
     * 是否查询比赛开始前的提交
     */
    private Boolean beforeContestSubmit;
}
