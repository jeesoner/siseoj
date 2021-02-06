package com.sise.oj.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * oj_judge_result_info
 *
 * @author Cijee
 * @version 1.0
 */
@Data
@TableName("oj_judge_result_info")
public class JudgeResultInfo implements Serializable {
    /**
     * 评测结果信息主键
     */
    private Long id;

    /**
     * 评测表主键
     */
    private Long judgeId;

    /**
     * 评测结果信息
     */
    private String resultInfo;

    /**
     * 反馈时间
     */
    private Date time;
}