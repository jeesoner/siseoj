package com.sise.oj.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 评测 Dto
 *
 * @author Cijee
 * @version 1.0
 */
@Data
public class JudgeDto {

    @NotNull(message = "题目ID不能为空")
    private Long pid;

    @NotNull(message = "提交的比赛id所属不能为空，若并非比赛提交，请设置为0")
    private Long cid;

    @NotBlank(message = "提交的代码不能为空")
    private String code;

    @NotNull(message = "代码语言不能为空")
    private Integer language;

    @NotNull(message = "时间限制不能为空")
    private Integer timeLimit;

    @NotNull(message = "空间限制不能为空")
    private Integer memoryLimit;

    private String contestPassword; // 比赛密码
}
