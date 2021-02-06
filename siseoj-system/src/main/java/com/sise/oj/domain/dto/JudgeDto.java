package com.sise.oj.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 评测 Dto
 *
 * @author Cijee
 * @version 1.0
 */
@Data
public class JudgeDto {

    /**
     * 题目表主键
     */
    @NotNull(message = "题目ID不能为空")
    private Long pid;

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String username;

    /**
     * 提交的代码
     */
    @NotNull(message = "提交的代码不能为空")
    private String code;

    /**
     * 评测的语言 C/C++
     */
    @NotNull(message = "评测语言不能为空")
    private Integer language;

    /**
     * 时间限制
     */
    @NotNull(message = "时间限制不能为空")
    private Integer timeLimit;

    /**
     * 空间限制
     */
    @NotNull(message = "空间限制不能为空")
    private Integer memoryLimit;

    /**
     * 比赛表主键
     */
    private Long cid;
}
