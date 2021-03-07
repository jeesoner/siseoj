package com.sise.oj.judge.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 评测结果 Dto
 *
 * @author Cijee
 * @version 1.0
 */
@Data
public class ServerResultDto implements Serializable {

    private Integer status;
    private String msg;
    private ResultInfo data;
}
