package com.sise.oj.service;


import com.sise.oj.domain.User;

/**
 * TODO
 *
 * @author CiJee
 * @version 1.0
 */
public interface UserService {

    User findByName(String username);

    User findById(Long id);
}
