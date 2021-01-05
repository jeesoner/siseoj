package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * user_auth
 *
 * @author CiJee
 * @version 1.0
 */
@Getter
@Setter
public class UserAuth implements Serializable {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 密码
     */
    private String password;

    /**
     * 1为锁定，0为启用
     */
    @TableField("is_locked")
    private Boolean locked;

    /**
     * 最后一次登录时间
     */
    private Date loginTime;

    /**
     * 账户解锁时间
     */
    private Date unlockTime;

    /**
     * 登录失败次数，超过5次锁定账户
     */
    private Integer loginFailCount;

    /**
     * 用户信息表外键
     */
    private Long userId;

}
