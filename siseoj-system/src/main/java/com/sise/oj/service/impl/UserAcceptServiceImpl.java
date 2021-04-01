package com.sise.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sise.oj.base.BaseServiceImpl;
import com.sise.oj.domain.UserAccept;
import com.sise.oj.mapper.UserAcceptMapper;
import com.sise.oj.service.UserAcceptService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
@Service
public class UserAcceptServiceImpl extends BaseServiceImpl<UserAcceptMapper, UserAccept> implements UserAcceptService {

    private final UserAcceptMapper userAcceptMapper;

    public UserAcceptServiceImpl(UserAcceptMapper userAcceptMapper) {
        this.userAcceptMapper = userAcceptMapper;
    }

    @Override
    public List<UserAccept> findByUserId(Long userId) {
        QueryWrapper<UserAccept> wrapper = new QueryWrapper<>();
        wrapper.select("distinct pid, uid").eq("uid", userId);
        return userAcceptMapper.selectList(wrapper);
    }
}
