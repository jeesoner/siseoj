package com.sise.oj.domain.vo;

import lombok.Data;

/**
 * @author Cijee
 * @version 1.0
 */
@Data
public class UserEmailVo {

    private String password;

    private String oldEmail;

    private String newEmail;
}
