package com.sise.oj.service;

import com.sise.oj.domain.UserAuth;

/**
 * @author Cijee
 * @version 1.0
 */
public interface UserAuthService {

    UserAuth findByName(String username);

}
