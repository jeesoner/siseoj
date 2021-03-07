package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ct_contest
 *
 * @author Cijee
 * @version 1.0
 */
@Data
@TableName("ct_contest")
public class Contest implements Serializable {

    /**
     * 比赛表主键
     */
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
    private String title;

    /**
     * 比赛类型：1普通比赛，2rating比赛
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
    private Integer auth;

    /**
     * 比赛密码
     */
    private String password;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 比赛时长(s)
     */
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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;
}