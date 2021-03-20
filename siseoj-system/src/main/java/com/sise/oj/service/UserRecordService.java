package com.sise.oj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.oj.base.BaseService;
import com.sise.oj.domain.UserRecord;
import com.sise.oj.domain.dto.RankDto;

/**
 * @author Cijee
 * @version 1.0
 */
public interface UserRecordService extends BaseService<UserRecord> {

    /**
     * 分页查询
     *
     * @param username 用户名
     * @param page 分页参数
     * @param type 榜单类型 1：rating 2：ac
     * @return Page
     */
    Page<RankDto> list(String username, Page<RankDto> page, Integer type);
}
