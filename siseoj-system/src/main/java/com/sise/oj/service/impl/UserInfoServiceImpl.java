package com.sise.oj.service.impl;

import com.sise.oj.domain.UserInfo;
import com.sise.oj.mapper.UserInfoMapper;
import com.sise.oj.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author CiJee
 * @version 1.0
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoMapper userInfoMapper;

    public UserInfoServiceImpl(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public UserInfo getUserInfo(Long id) {
        return userInfoMapper.selectById(id);
    }
}
