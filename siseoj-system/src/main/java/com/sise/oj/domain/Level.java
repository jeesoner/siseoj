package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * pb_level 难度级别表
 *
 * @author Cijee
 * @version 1.0
 */
@Getter
@Setter
@TableName("pb_level")
public class Level implements Serializable {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 难度：简单、一般、困难
     */
    private String name;
}
