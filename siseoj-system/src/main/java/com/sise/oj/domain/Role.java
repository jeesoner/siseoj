package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * sys_role
 *
 * @author Cijee
 * @version 1.0
 */
@Getter
@Setter
@TableName("sys_role")
public class Role implements Serializable {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 用户集合
     */
    @TableField(exist = false)
    private Set<User> users;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;
}