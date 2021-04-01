package com.sise.oj.service;

import com.sise.oj.base.BaseService;
import com.sise.oj.domain.UserAccept;

import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
public interface UserAcceptService extends BaseService<UserAccept> {

    /**
     * 根据用户ID查询通过题目（去重）
     *
     * @param userId 用户id
     * @return List
     */
    List<UserAccept> findByUserId(Long userId);
}
