package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sise.oj.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * sys_user_accept
 * @author Cijee
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_user_accept")
public class UserAccept extends BaseEntity implements Serializable {

    /**
     * 用户通过题目表
     */
    private Long id;

    /**
     * 用户外键
     */
    private Long uid;

    /**
     * 题目外键
     */
    private Long pid;

    /**
     * 评测外键
     */
    private Long judgeId;
}