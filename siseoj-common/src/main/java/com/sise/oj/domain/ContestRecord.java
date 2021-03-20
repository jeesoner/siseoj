package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sise.oj.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * ct_contest_record
 *
 * @author Cijee
 * @version 1.0
 */
@Getter
@Setter
@TableName("ct_contest_record")
public class ContestRecord extends BaseEntity implements Serializable {

    /**
     * 比赛id
     */
    @TableId
    private Long cid;

    /**
     * 评测id
     */
    private Long judgeId;

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 题目id
     */
    private Long pid;

    /**
     * 比赛中的题目的id
     */
    private Long contestPid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 比赛中展示的id
     */
    private Integer displayId;

    /**
     * 提交结果，0表示未AC通过不罚时，1表示AC通过，-1为未AC通过算罚时
     */
    private Integer status;

    /**
     * 具体提交时间
     */
    private Date submitTime;

    /**
     * 提交时间，为提交时间减去比赛时间
     */
    private Long time;

    /**
     * 比赛的得分
     */
    private Integer score;

    /**
     * 是否为一血AC
     */
    private Boolean firstBlood;

    /**
     * AC是否已校验
     */
    private Boolean checked;
}