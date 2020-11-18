package com.sise.oj.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * TODO
 *
 * @author CiJee
 * @version 1.0
 */
@Data
@TableName(value = "user_info")
public class UserInfoPO implements Serializable {

    private Long id;
    private String username;
    private Integer gender;
    private String email;
    private String phone;
    private Date registerTime;
    private Date modifyTime;
    private String varchar;
    private Integer rating;

}
