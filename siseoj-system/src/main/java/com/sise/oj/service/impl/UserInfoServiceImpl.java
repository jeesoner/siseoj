package com.sise.oj.service.impl;

import com.sise.oj.domain.UserInfo;
import com.sise.oj.domain.dto.AuthUserDTO;
import com.sise.oj.exception.UserLoginFailedException;
import com.sise.oj.mapper.UserInfoMapper;
import com.sise.oj.service.UserInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public UserInfo getByUsername(String username) {
        List<UserInfo> userInfos = userInfoMapper.selectByUsername(username);
        if (userInfos.size() == 0) {
            throw new RuntimeException("系统错误");
        } else if (userInfos.size() > 1) {
            throw new RuntimeException("系统错误");
        }
        return userInfos.get(0);
    }

    @Override
    public boolean login(AuthUserDTO authUser) {
        List<UserInfo> users = userInfoMapper.selectByUsernameAndPassword(authUser.getUsername(), authUser.getPassword());
        if (users.size() == 0) {
            throw new UserLoginFailedException("用户登录失败");
        } else if (users.size() > 1) {
            throw new UserLoginFailedException("用户登录失败");
        }
        return true;
    }
}
