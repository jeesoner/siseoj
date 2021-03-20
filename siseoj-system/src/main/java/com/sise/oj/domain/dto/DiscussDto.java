package com.sise.oj.domain.dto;

import com.sise.oj.base.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Cijee
 * @version 1.0
 */
@Data
public class DiscussDto extends BaseEntity implements Serializable {

    /**
     * 帖子主键
     */
    @NotNull(groups = Update.class)
    private Long id;

    /**
     * 用户表外键
     */
    private Long uid;

    private String username;

    private String nickname;

    private String avatar;

    /**
     * 标题
     */
    @NotBlank
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 帖子内容
     */
    @NotBlank
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
     * 优先级
     */
    private Integer priority;

    /**
     * 是否禁止
     */
    private Boolean enable;
}
