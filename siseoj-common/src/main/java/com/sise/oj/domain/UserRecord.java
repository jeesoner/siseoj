package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sise.oj.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * sys_user_record
 */
@Getter
@Setter
@TableName("sys_user_record")
public class UserRecord extends BaseEntity implements Serializable {

    /**
     * 用户记录表主键
     */
    private Long id;

    /**
     * 用户表主键
     */
    private Long uid;
    
    /**
     * 总提交数
     */
    private Integer submissions;

    /**
     * rank分数
     */
    private Integer rating;
}