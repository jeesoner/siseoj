package com.sise.oj.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseServiceImpl;
import com.sise.oj.domain.UserRecord;
import com.sise.oj.domain.dto.RankDto;
import com.sise.oj.mapper.UserRecordMapper;
import com.sise.oj.service.UserRecordService;
import com.sise.oj.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserRecordServiceImpl extends BaseServiceImpl<UserRecordMapper, UserRecord> implements UserRecordService {

    private final UserRecordMapper userRecordMapper;

    @Override
    public Page<RankDto> list(String username, Page<RankDto> page, Integer type) {
        List<RankDto> rankList;
        if (type == 1) {
            rankList = userRecordMapper.getRatingRank(page, username);
        } else {
            rankList = userRecordMapper.getAcRank(page, username);
        }
        page.setRecords(rankList);
        return page;
    }
}
