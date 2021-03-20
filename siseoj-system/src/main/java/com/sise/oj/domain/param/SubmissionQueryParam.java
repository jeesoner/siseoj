package com.sise.oj.domain.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Cijee
 * @version 1.0
 */
@Data
public class SubmissionQueryParam implements Serializable {

    private String username;
    private Long pid;
    private Integer status;
    private Integer language;
}
