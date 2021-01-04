package com.sise.oj.service.impl;

import com.sise.oj.domain.UserAuth;
import com.sise.oj.exception.DataNotFoundException;
import com.sise.oj.mapper.UserAuthMapper;
import com.sise.oj.service.UserAuthService;
import org.springframework.stereotype.Service;

/**
 * @author Cijee
 * @version 1.0
 */
@Service
public class UserAuthServiceImpl implements UserAuthService {

    private final UserAuthMapper userAuthMapper;

    public UserAuthServiceImpl(UserAuthMapper userAuthMapper) {
        this.userAuthMapper = userAuthMapper;
    }

    @Override
    public UserAuth findByName(String username) {
        UserAuth userAuth = userAuthMapper.selectByName(username);
        if (userAuth == null) {
            throw new DataNotFoundException(UserAuth.class, "name", username);
        } else {
            return userAuth;
        }
    }
}
