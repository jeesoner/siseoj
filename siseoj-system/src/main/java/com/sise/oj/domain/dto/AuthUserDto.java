package com.sise.oj.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 登录、授权用户 Dto
 *
 * @author CiJee
 * @version 1.0
 */
@Getter
@Setter
public class AuthUserDto implements Serializable {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String code;

    private String uuid = "";
}
