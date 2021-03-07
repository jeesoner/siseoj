package com.sise.oj.domain.vo;

import com.sise.oj.domain.Problem;
import com.sise.oj.domain.ProblemCount;
import com.sise.oj.domain.Source;
import com.sise.oj.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 题目详情 Vo
 *
 * @author Cijee
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class ProblemInfoVo implements Serializable {

    private Problem problem;

    private Source source;

    private List<Tag> tags;

    private ProblemCount problemCount;
}
