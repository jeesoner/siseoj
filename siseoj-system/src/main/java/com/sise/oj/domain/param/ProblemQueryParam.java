package com.sise.oj.domain.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Cijee
 * @version 1.0
 */
@Data
public class ProblemQueryParam implements Serializable {

    private Long id;

    /**
     * 题目名称或题目号
     */
    private String title;

    /**
     * 标签主键
     */
    private Long tagId;

    private Long levelId;

    private Long sourceId;
}
