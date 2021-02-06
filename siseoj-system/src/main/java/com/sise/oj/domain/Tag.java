package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * pb_tag 标签表
 *
 * @author Cijee
 * @version 1.0
 */
@Getter
@Setter
@TableName("pb_tag")
public class Tag implements Serializable {

    /**
     * 主键
     */
    @TableId
    @NotNull(groups = Update.class, message = "标签主键不能为空")
    private Long id;

    /**
     * 名称
     */
    @NotBlank(message = "标签名称不能为空")
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;

    public interface Update {}
}
