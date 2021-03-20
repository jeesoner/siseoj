package com.sise.oj.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sise.oj.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
public class User extends BaseEntity implements Serializable {

    /**
     * 主键
     */
    @TableId
    @NotNull(groups = Update.class, message = "用户主键不能为空")
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
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 是否为管理员
     */
    private Boolean isAdmin;

    /**
     * 密码
     */
    @NotBlank(groups = Create.class, message = "密码不能为空")
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
     * 学校
     */
    private String school;

    /**
     * 专业
     */
    private String course;

    /**
     * github地址
     */
    private String github;

    /**
     * 博客地址
     */
    private String blog;

    /**
     * 头像地址
     */
    private String avatar;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
