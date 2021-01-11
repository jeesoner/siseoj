package com.sise.oj.service.impl;

import com.sise.oj.domain.User;
import com.sise.oj.mapper.UserMapper;
import com.sise.oj.service.UserService;
import org.springframework.stereotype.Service;

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
    public User findByName(String username) {
        return userMapper.selectByName(username);
    }

    @Override
    public User findById(Long id) {
        return userMapper.selectById(id);
    }
}
