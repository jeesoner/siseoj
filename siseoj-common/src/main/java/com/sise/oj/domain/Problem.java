package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sise.oj.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * pb_problem 题目表
 *
 * @author Cijee
 * @version 1.0
 */
@Getter
@Setter
@TableName("pb_problem")
public class Problem extends BaseEntity implements Serializable {

    /**
     * 题目主键
     */
    @TableId
    private Long pid;

    /**
     * 题目来源表外键
     */
    @NotNull(message = "题目来源主键不能为空")
    private Long sourceId;

    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 题目难度，0简单，1中等，2困难
     */
    private Integer difficulty;

    /**
     * 默认为1公开，2为比赛题目，3私有
     */
    private Integer auth;

    /**
     * 时间限制（ms）
     */
    @Min(value = 1, message = "时间限制不能小于1ms")
    private Integer timeLimit;

    /**
     * 空间限制（MB）
     */
    @Min(value = 1, message = "空间限制不能小于1MB")
    private Integer memoryLimit;

    /**
     * 题目描述
     */
    @NotBlank(message = "题目描述不能为空")
    private String description;

    /**
     * 输入描述
     */
    private String input;

    /**
     * 输出描述
     */
    private String output;

    /**
     * 题面样例
     */
    private String examples;

    /**
     * 提示
     */
    private String hint;

    /**
     * 特判程序代码，为空则不是特判
     */
    private String specialCode;
}
