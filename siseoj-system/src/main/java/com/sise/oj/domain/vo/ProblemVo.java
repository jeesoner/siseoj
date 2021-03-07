package com.sise.oj.domain.vo;

import com.sise.oj.domain.Source;
import com.sise.oj.domain.Tag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ProblemVo
 *
 * @author Cijee
 * @version 1.0
 */
@ApiModel(value="题目列表对象ProblemVo", description="")
@Data
public class ProblemVo implements Serializable {

    @ApiModelProperty(value = "题目id")
    private Long pid;

    @ApiModelProperty(value = "题目标题")
    private String title;

    @ApiModelProperty(value = "题目难度")
    private Integer difficulty;

    @ApiModelProperty(value = "题目来源id")
    private Long sourceId;

    @ApiModelProperty(value = "题目来源")
    private Source source;

    @ApiModelProperty(value = "题目标签")
    private List<Tag> tags;

    // 以下为题目统计情况
    @ApiModelProperty(value = "该题总提交数")
    private Integer total;

    @ApiModelProperty(value = "通过提交数")
    private Integer accept;
}
