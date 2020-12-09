package com.sise.oj.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 登录、授权用户VO
 *
 * @author CiJee
 * @version 1.0
 */
@Getter
@Setter
public class AuthUserDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String code;

    private String uuid = "";
}
