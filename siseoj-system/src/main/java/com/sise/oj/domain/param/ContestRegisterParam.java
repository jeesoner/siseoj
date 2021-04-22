package com.sise.oj.domain.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Cijee
 * @version 1.0
 */
@Data
public class ContestRegisterParam implements Serializable {

    @NotNull
    private Long cid;

    @NotBlank
    private String password;
}
