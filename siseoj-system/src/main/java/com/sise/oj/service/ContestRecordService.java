package com.sise.oj.service;

import com.sise.oj.base.BaseService;
import com.sise.oj.domain.ContestRecord;

/**
 * @author Cijee
 * @version 1.0
 */
public interface ContestRecordService extends BaseService<ContestRecord> {

    void updateContestRecord(Long uid, Integer score, Integer status, Long submitId, Long cid);
}
