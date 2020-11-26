package com.sise.oj.admin.service.impl;

import com.sise.oj.admin.service.UserInfoService;
import com.sise.oj.domain.po.UserInfoPO;
import com.sise.oj.mapper.ProblemMapper;
import com.sise.oj.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author CiJee
 * @version 1.0
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private ProblemMapper problemMapper;

    @Override
    public UserInfoPO findById(Long id) {
        return userInfoMapper.selectById(id);
    }
}
