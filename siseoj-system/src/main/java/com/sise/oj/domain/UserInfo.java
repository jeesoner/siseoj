package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * TODO
 *
 * @author CiJee
 * @version 1.0
 */
@Getter
@Setter
public class UserInfo implements Serializable {

    @TableId
    private Long id;
    private String nickname;
    private String username;
    private String password;
    private Integer gender;
    private String email;
    private String phone;
    private Date registerTime;
    private Date modifyTime;

    /**
     * 个人简介
     */
    private String motto;

}
