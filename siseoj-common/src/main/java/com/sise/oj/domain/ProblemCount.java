package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sise.oj.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * pb_problem_count 题目统计表
 *
 * @author Cijee
 * @version 1.0
 */
@Getter
@Setter
@TableName("pb_problem_count")
public class ProblemCount extends BaseEntity implements Serializable {

    /**
     * 题目统计表主键
     */
    private Long id;

    /**
     * 题目表外键
     */
    private Long pid;

    /**
     * 总次数
     */
    private Integer total;

    /**
     * 通过次数
     */
    private Integer accept;
}