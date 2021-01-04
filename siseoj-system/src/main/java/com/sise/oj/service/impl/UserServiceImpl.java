package com.sise.oj.service.impl;

import com.sise.oj.domain.User;
import com.sise.oj.domain.dto.AuthUserDto;
import com.sise.oj.exception.UserLoginFailedException;
import com.sise.oj.mapper.UserMapper;
import com.sise.oj.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author CiJee
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User getUserInfo(Long id) {
        return userMapper.selectById(id);
    }
}
