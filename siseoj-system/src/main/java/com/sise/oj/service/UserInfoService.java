package com.sise.oj.service;


import com.sise.oj.domain.UserInfo;
import com.sise.oj.domain.dto.AuthUserDTO;

/**
 * TODO
 *
 * @author CiJee
 * @version 1.0
 */
public interface UserInfoService {

    UserInfo getUserInfo(Long id);

    boolean login(AuthUserDTO authUser);
}
