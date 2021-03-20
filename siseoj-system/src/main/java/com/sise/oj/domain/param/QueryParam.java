package com.sise.oj.domain.param;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
@Data
public class QueryParam {

    private Long id;

    /**
     * 查询关键字，可能为 username, nickname, name
     */
    private String keyword;

    private Boolean enabled;

    private Integer status;

    private List<Date> createTime;
}
