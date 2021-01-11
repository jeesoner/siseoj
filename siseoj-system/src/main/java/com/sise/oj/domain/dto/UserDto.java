package com.sise.oj.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.sise.oj.domain.Role;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author Cijee
 * @version 1.0
 */
@Getter
@Setter
public class UserDto implements Serializable {

    private Long id;

    private Set<Role> roles;

    private String nickname;

    private String username;

    @JSONField(serialize = false)
    private String password;

    private Integer gender;

    private String email;

    private String phone;

    private String motto;

    private Boolean enabled;

    private Date lastLoginTime;

    private Date unlockTime;

    private Integer loginFailCount;
}
