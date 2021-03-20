package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sise.oj.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
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
public class Role extends BaseEntity implements Serializable {

    /**
     * 主键
     */
    @TableId
    @NotNull(groups = Update.class, message = "主键不能为空")
    private Long id;

    /**
     * 用户集合
     */
    @TableField(exist = false)
    private Set<User> users;

    /**
     * 菜单集合
     */
    @TableField(exist = false)
    private Set<Menu> menus;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String name;

    /**
     * 角色描述
     */
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}