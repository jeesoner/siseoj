package com.sise.oj.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * sys_user
 *
 * @author CiJee
 * @version 1.0
 */
@Getter
@Setter
@TableName("sys_user")
public class User implements Serializable {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 角色集合
     */
    @TableField(exist = false)
    private Set<Role> roles;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    @JSONField(serialize = false)
    private String password;

    /**
     * 性别，0为保密，1为男，2为女
     */
    private Integer gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 个性签名
     */
    private String motto;

    /**
     * 创建时间
     */
    @JSONField(serialize = false)
    private Date createTime;

    /**
     * 修改时间
     */
    @JSONField(serialize = false)
    private Date modifyTime;

    /**
     * 启用状态：1启用、2禁用
     */
    private Boolean enabled;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 解锁时间
     */
    private Date unlockTime;

    /**
     * 登录失败次数
     */
    private Integer loginFailCount;
}
