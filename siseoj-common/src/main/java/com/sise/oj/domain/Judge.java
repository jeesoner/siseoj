package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sise.oj.base.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * oj_judge
 *
 * @author Cijee
 * @version 1.0
 */
@Data
@TableName("oj_judge")
public class Judge extends BaseEntity implements Serializable {

    /**
     * 评测表主键
     */
    private Long id;

    /**
     * 题目表外键
     */
    @NotNull(message = "题目ID不能为空")
    private Long pid;

    /**
     * 比赛表外键
     */
    private Long cid;

    /**
     * 用户表外键
     */
    private Long uid;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 评测结果码
     */
    private Integer status;

    /**
     * 错误信息
     */
    private String error_message;

    /**
     * 代码语言，1：C/C++，2：Java
     */
    @NotNull(message = "评测语言不能为空")
    private Integer language;

    /**
     * 提交的代码
     */
    @NotBlank(message = "提交的代码不能为空")
    private String code;

    /**
     * 得分
     */
    private Integer score;

    /**
     * 运行时间（ms）
     */
    private Integer time;

    /**
     * 运行内存（MB）
     */
    private Integer memory;

    /**
     * 提交时间
     */
    private Date submitTime;

    /**
     * 代码长度
     */
    private Integer codeLength;

    /**
     * 0：仅自己可见，1：所有人可见
     */
    private Boolean share;
}