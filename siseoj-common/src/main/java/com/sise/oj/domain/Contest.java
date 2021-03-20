package com.sise.oj.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sise.oj.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

/**
 * ct_contest
 *
 * @author Cijee
 * @version 1.0
 */
@Getter
@Setter
@TableName("ct_contest")
public class Contest extends BaseEntity implements Serializable {

    /**
     * 比赛表主键
     */
    @TableId
    @Null(groups = Create.class, message = "存在主键")
    @NotNull(groups = Update.class, message = "主键不能为空")
    private Long id;

    /**
     * 比赛创建者id
     */
    private Long uid;

    /**
     * 比赛创建者的用户名
     */
    private String author;

    /**
     * 比赛标题
     */
    @NotBlank(message = "比赛标题不能为空")
    private String title;

    /**
     * 比赛类型：0普通比赛，1rating比赛
     */
    private Integer type;

    /**
     * 比赛说明
     */
    private String description;

    /**
     * 比赛来源，原创为0，克隆赛为比赛id
     */
    private Long source;

    /**
     * 0为公开赛，1为私有赛（访问有密）
     */
    @NotNull(groups = Update.class)
    private Integer auth;

    /**
     * 比赛密码
     */
    @JSONField(serialize = false)
    private String password;

    /**
     * 开始时间
     */
    @NotNull(message = "开始时间不能为空")
    private Date startTime;

    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不能为空")
    private Date endTime;

    /**
     * 比赛时长(s)
     */
    @NotNull(message = "比赛时长不能为空")
    private Integer duration;

    /**
     * 是否开启封榜
     */
    private Boolean sealRank;

    /**
     * 封榜起始时间，一直到比赛结束，不刷新榜单
     */
    private Date sealRankTime;

    /**
     * 比赛状态：-1未开始，0进行中，1已结束
     */
    private Integer status;
}