package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sise.oj.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * ct_contest_register
 *
 * @author Cijee
 * @version 1.0
 */
@Getter
@Setter
@TableName("ct_contest_register")
public class ContestRegister extends BaseEntity implements Serializable {

    @TableId
    private Long id;

    private Long cid;

    private Long uid;

    /**
     * 注册状态：0正常， 1失效
     */
    private Integer status;
}
