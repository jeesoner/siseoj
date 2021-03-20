package com.sise.oj.domain.dto;

import com.sise.oj.domain.ContestProblem;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
@Data
public class ContestProblemListDto implements Serializable {

    private List<ContestProblem> contestProblems;

    @NotNull(message = "比赛ID不能为空")
    private Long cid;
}
