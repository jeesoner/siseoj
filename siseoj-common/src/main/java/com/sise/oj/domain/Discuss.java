package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sise.oj.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * discuss
 *
 * @author Cijee
 */
@Data
@TableName("discuss")
public class Discuss extends BaseEntity implements Serializable {

    /**
     * 帖子主键
     */
    private Long id;

    /**
     * 用户表外键
     */
    private Long uid;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 帖子内容
     */
    private String content;

    /**
     * 浏览数
     */
    private Integer views;

    /**
     * 点赞数
     */
    private Integer likes;

    /**
     * 是否禁止
     */
    private Boolean enable;

    /**
     * 优先级
     */
    private Integer priority;
}