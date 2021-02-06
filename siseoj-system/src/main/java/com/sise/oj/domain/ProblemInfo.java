package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * pb_problem_info 题目信息表
 *
 * @author Cijee
 * @version 1.0
 */
@Data
@TableName("pb_problem_info")
public class ProblemInfo implements Serializable {

    /**
     * 题目详情表主键
     */
    @TableId
    @NotNull(groups = Update.class, message = "题目详情主键不能为空")
    private Long id;

    /**
     * 时间限制，单位ms
     */
    @Min(value = 1000, message = "时间限制不能小于1000")
    private Integer timeLimit;

    /**
     * 空间限制，单位MB
     */
    @Min(value = 64, message = "空间限制不能小于64")
    private Integer memoryLimit;

    /**
     * 题目描述
     */
    @NotBlank(message = "题目描述不能为空")
    private String description;

    /**
     * 输入样例
     */
    private String inputCase;

    /**
     * 输出样例
     */
    private String outputCase;

    /**
     * 输入格式
     */
    private String inputFormat;

    /**
     * 输出格式
     */
    private String outputFormat;

    /**
     * 数据范围
     */
    private String dataRange;

    /**
     * 是否特判
     */
    private Boolean specialJudge;

    /**
     * 提示
     */
    private String hint;

    /**
     * 分组校验
     */
    public interface Update {}
}