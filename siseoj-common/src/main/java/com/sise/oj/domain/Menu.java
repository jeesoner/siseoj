package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sise.oj.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * sys_menu
 *
 * @author Cijee
 * @version 1.0
 */
@Getter
@Setter
@TableName("sys_menu")
public class Menu extends BaseEntity implements Serializable {

    /**
     * 主键
     */
    @TableId
    private Long menuId;

    /**
     * 上级菜单ID
     */
    private Long pid;

    /**
     * 子菜单数目
     */
    private Integer subCount;

    /**
     * 菜单类型
     */
    private Integer type;

    /**
     * 菜单标题
     */
    private String title;

    /**
     * 组件名称
     */
    private String name;

    /**
     * 组件
     */
    private String component;

    /**
     * 排序
     */
    private Integer menuSort;

    /**
     * 图标
     */
    private String icon;

    /**
     * 链接地址
     */
    private String path;

    /**
     * 是否外链
     */
    private Boolean iFrame;

    /**
     * 缓存
     */
    private Boolean cache;

    /**
     * 隐藏
     */
    private Boolean hidden;

    /**
     * 权限
     */
    private String permission;
}