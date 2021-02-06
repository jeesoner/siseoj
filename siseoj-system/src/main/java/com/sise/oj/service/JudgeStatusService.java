package com.sise.oj.service;

import com.sise.oj.base.BaseService;
import com.sise.oj.domain.JudgeStatus;
import com.sise.oj.domain.dto.JudgeDto;

/**
 * JudgeStatusService
 *
 * @author Cijee
 * @version 1.0
 */
public interface JudgeStatusService extends BaseService<JudgeStatus> {

    /**
     * 创建新的评测记录
     *
     * @param judgeStatus 评测参数Dto
     * @return 新的评测记录
     */
    JudgeStatus create(JudgeDto judgeStatus);


    /**
     * 记录评测的异常信息
     *
     * @param judgeStatus -
     */
    void error(JudgeStatus judgeStatus);
}
