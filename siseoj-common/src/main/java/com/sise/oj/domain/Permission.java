package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sise.oj.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * sys_permission 权限表
 *
 * @author Cijee
 * @version 1.0
 */
@Getter
@Setter
@TableName("sys_permission")
public class Permission extends BaseEntity implements Serializable {

    /**
     * 权限表主键
     */
    private Long id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限字符串
     */
    private String permission;

    /**
     * 是否可用，0：不可用，1：可用
     */
    private Boolean status;
}