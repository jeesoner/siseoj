package com.sise.oj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseService;
import com.sise.oj.domain.ContestRecord;
import com.sise.oj.domain.vo.ContestRankVo;

/**
 * @author Cijee
 * @version 1.0
 */
public interface ContestRecordService extends BaseService<ContestRecord> {

    /**
     * 更新比赛记录
     *
     * @param uid -
     * @param score -
     * @param status -
     * @param submitId -
     * @param cid -
     */
    void update(Long uid, Integer score, Integer status, Long submitId, Long cid);

    /**
     * 获取ACM模式比赛排行榜
     *
     * @param page 分页参数
     * @param cid 比赛ID
     * @return Page
     */
    Page<ContestRankVo> getContestACMRank(Page<ContestRankVo> page, Long cid);
}
