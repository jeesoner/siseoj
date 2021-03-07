package com.sise.oj.domain.dto;

import com.sise.oj.domain.Problem;
import com.sise.oj.domain.ProblemCase;
import com.sise.oj.domain.Tag;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * ProblemDto
 *
 * @author Cijee
 * @version 1.0
 */
@Data
public class ProblemDto {

    @Valid
    @NotNull(message = "题目信息不能为空")
    private Problem problem;

    @Valid
    @NotNull(message = "题目样例信息不能为空")
    private List<ProblemCase> problemCases;

    private List<Tag> tags;
}
