package com.sise.oj.domain.vo;

import lombok.Data;

/**
 * @author Cijee
 * @version 1.0
 */
@Data
public class UserPasswordVo {

    private String oldPassword;
    private String newPassword;
}
