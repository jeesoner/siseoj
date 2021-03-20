package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sise.oj.base.BaseEntity;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * pb_problem_case 题目样例表
 *
 * @author Cijee
 * @version 1.0
 */
@Getter
@Setter
@Generated
@TableName("pb_problem_case")
public class ProblemCase extends BaseEntity implements Serializable {

    /**
     * 题目样例表主键
     */
    @TableId
    private Long id;

    /**
     * 题目表外键
     */
    private Long pid;

    /**
     * 输入样例
     */
    private String inputCase;

    /**
     * 输出样例
     */
    @NotBlank(message = "输出样例不能为空")
    private String outputCase;

    /**
     * 得分
     */
    private Integer score;

    /**
     * 是否可用
     */
    private Boolean status;
}