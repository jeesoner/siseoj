package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sise.oj.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * ct_contest_problem
 *
 * @author Cijee
 * @version 1.0
 */
@Getter
@Setter
@TableName("ct_contest_problem")
public class ContestProblem extends BaseEntity implements Serializable {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 该题目在比赛中的id
     */
    private Integer displayId;

    /**
     * 比赛主键
     */
    private Long cid;

    /**
     * 题目主键
     */
    private Long pid;

    /**
     * 题目标题
     */
    @TableField(exist = false)
    private String title;

    /**
     * 得分
     */
    private Integer score;

    /**
     * 该题目在比赛中的标题
     */
    private String displayTitle;
}