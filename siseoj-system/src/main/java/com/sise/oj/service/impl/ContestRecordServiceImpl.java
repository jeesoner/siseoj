package com.sise.oj.service.impl;

import com.sise.oj.base.BaseServiceImpl;
import com.sise.oj.domain.ContestRecord;
import com.sise.oj.mapper.ContestRecordMapper;
import com.sise.oj.service.ContestRecordService;
import org.springframework.stereotype.Service;

/**
 * @author Cijee
 * @version 1.0
 */
@Service
public class ContestRecordServiceImpl extends BaseServiceImpl<ContestRecordMapper, ContestRecord> implements ContestRecordService {

    @Override
    public void updateContestRecord(Long uid, Integer score, Integer status, Long submitId, Long cid) {

    }
}
