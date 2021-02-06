package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * pb_source 来源OJ表
 *
 * @author Cijee
 * @version 1.0
 */
@Getter
@Setter
@TableName("pb_source")
public class Source implements Serializable {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 来源OJ
     */
    private String name;
}
