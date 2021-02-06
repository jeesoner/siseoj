package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * oj_judge_status
 *
 * @author Cijee
 * @version 1.0
 */
@Data
@TableName("oj_judge_status")
public class JudgeStatus implements Serializable {
    /**
     * 评测表主键
     */
    private Long id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 题目表外键
     */
    @NotNull(message = "题目ID不能为空")
    private Long pid;

    /**
     * 用户表外键
     */
    private Long userId;

    /**
     * 比赛表主键
     */
    private Long cid;

    /**
     * 评测结果
     */
    private Integer result;

    /**
     * 使用语言，1：C/C++，2：Java
     */
    @NotNull(message = "评测语言不能为空")
    private Integer language;

    /**
     * 提交的代码
     */
    @NotBlank(message = "提交的代码不能为空")
    private String code;

    /**
     * 如果是特判类型的题目，这里显示得分
     */
    private Integer score;

    /**
     * 评测使用时间
     */
    private Integer usedTime;

    /**
     * 评测使用空间
     */
    private Integer usedMemory;

    /**
     * 提交时间
     */
    private Date submitTime;

    /**
     * 代码长度
     */
    private Integer codeLength;
}