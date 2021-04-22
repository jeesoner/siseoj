package com.sise.oj.service;

import com.sise.oj.base.BaseService;
import com.sise.oj.domain.ProblemCount;

/**
 * ProblemCountService
 *
 * @author Cijee
 * @version 1.0
 */
public interface ProblemCountService extends BaseService<ProblemCount> {

    /**
     * 更新题目记录表
     *
     * @param pid 题号
     * @param accept 是否通过
     */
    void updateCount(Long pid, boolean accept);

    int acceptCount();

    int totalCount();
}
