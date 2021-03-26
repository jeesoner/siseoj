package com.sise.oj.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Cijee
 * @version 1.0
 */
@Data
public class ContestRankVo implements Serializable {

    /**
     * 题目详情（得分，使用时间）
     */
    private Map<String, ContestRankDetail> details;

    /**
     * 使用时间（罚时）
     */
    private Integer runningTime;

    /**
     * 得分（AC题数）
     */
    private Integer score;

    /**
     * 用户信息
     */
    private String username;
}
