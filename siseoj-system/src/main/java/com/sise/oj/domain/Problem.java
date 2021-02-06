package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * pb_problem 题目表
 *
 * @author Cijee
 * @version 1.0
 */
@Data
@TableName("pb_problem")
public class Problem implements Serializable {

    /**
     * 题目主键
     */
    @TableId
    @NotNull(groups = Update.class, message = "题目主键不能为空")
    private Long pid;

    /**
     * 题目标题
     */
    @NotBlank(message = "题目名称不能为空")
    private String title;

    /**
     * 难度表主键
     */
    private Long levelId;

    /**
     * 来源表主键
     */
    private Long sourceId;

    /**
     * 题目详情表主键
     */
    @NotNull(groups = Update.class, message = "题目详情主键不能为空")
    private Long problemInfoId;

    /**
     * 尝试次数
     */
    private Integer attemptCount;

    /**
     * 通过次数
     */
    private Integer acceptCount;

    /**
     * 作者
     */
    private String author;

    /**
     * 是否可见
     */
    private Boolean visible;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;

    /**
     * 难度，一道题目对应一个难度
     */
    @TableField(exist = false)
    private Level level;

    /**
     * 标签，多道题目可以有多个标签
     */
    @TableField(exist = false)
    private List<Tag> tags;

    /**
     * 标签id集合
     */
    @TableField(exist = false)
    private List<Long> tagIds;

    /**
     * 来源，一道题目对应一个来源
     */
    @TableField(exist = false)
    private Source source;

    /**
     * 题目详情，一道题目对应一个题目详情
     */
    @Valid
    @TableField(exist = false)
    private ProblemInfo problemInfo;

    public @interface Update {}
}
