package com.sise.oj.service.impl;

import com.sise.oj.domain.UserInfo;
import com.sise.oj.service.UserInfoService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author CiJee
 * @version 1.0
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserInfoService userInfoService;

    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(UserInfoService userInfoService, PasswordEncoder passwordEncoder) {
        this.userInfoService = userInfoService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userinfo = userInfoService.getByUsername(username);
        userinfo.setPassword(passwordEncoder.encode(userinfo.getPassword()));
        System.out.println(userinfo.getPassword());

        return new User(userinfo.getUsername(), userinfo.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
