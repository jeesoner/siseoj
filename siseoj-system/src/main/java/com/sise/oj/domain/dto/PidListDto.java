package com.sise.oj.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
@Data
public class PidListDto implements Serializable {

    /**
     * 题目ID集合
     */
    @NotEmpty(message = "题目ID集合不能为空")
    private List<Long> ids;

    /**
     * 比赛ID
     */
    private Long cid;

    /**
     * 是否比赛题目
     */
    @NotNull(message = "是否比赛不能为空")
    private Boolean contestProblem;
}
