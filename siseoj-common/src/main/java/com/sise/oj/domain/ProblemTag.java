package com.sise.oj.domain;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sise.oj.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * pb_problem 题目表
 *
 * @author Cijee
 * @version 1.0
 */
@Getter
@Setter
@TableName("pb_problems_tags")
public class ProblemTag extends BaseEntity implements Serializable {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 题目id
     */
    private Long pid;

    /**
     * 标签id
     */
    private Long tid;

    public ProblemTag() {
    }

    public ProblemTag(Long pid, Long tid) {
        this.pid = pid;
        this.tid = tid;
    }
}
