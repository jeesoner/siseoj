package com.sise.oj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseService;
import com.sise.oj.domain.Judge;
import com.sise.oj.domain.dto.JudgeDto;
import com.sise.oj.domain.param.SubmissionQueryParam;

/**
 * JudgeStatusService
 *
 * @author Cijee
 * @version 1.0
 */
public interface JudgeService extends BaseService<Judge> {

    /**
     * 分页查询提交记录
     *
     * @param param 查询参数
     * @param page 分页参数
     * @return Page
     */
    Page<Judge> list(SubmissionQueryParam param, Page<Judge> page);

    /**
     * 分页查询比赛提交记录
     *
     * @param param 查询参数
     * @param page 分页参数
     * @return Page
     */
    Page<Judge> getContestSubmissionList(Page<Judge> page, Long cid, SubmissionQueryParam param);

    /**
     * 提交题目判题
     *
     * @param judgeDto 判题Dto
     */
    Judge submitProblemJudge(JudgeDto judgeDto);

    /**
     * 异步查询评测机的判题结果，并写入数据库
     */
    void getJudgeStatus(Long submitId) throws Exception;
}
