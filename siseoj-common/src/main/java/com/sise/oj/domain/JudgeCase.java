package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sise.oj.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * oj_judge_case
 *
 * @author Cijee
 * @version 1.0
 */
@Getter
@Setter
@TableName("oj_judge_case")
public class JudgeCase extends BaseEntity implements Serializable {

    /**
     * 评测样例表主键
     */
    @TableId
    private Long id;

    /**
     * 评测表主键
     */
    private Long judgeId;

    /**
     * 用户表外键
     */
    private Long uid;

    /**
     * 题目表外键
     */
    private Long pid;

    /**
     * 题目样例表外键
     */
    private Long caseId;

    /**
     * 评测结果码
     */
    private Integer status;

    /**
     * 样例运行时间（ms）
     */
    private Integer time;

    /**
     * 样例运行内存（MB）
     */
    private Integer memory;

    /**
     * 样例得分
     */
    private Integer score;
}