package com.sise.oj.service.impl;

import com.sise.oj.domain.UserAuth;
import com.sise.oj.domain.dto.JwtUserDto;
import com.sise.oj.exception.BadRequestException;
import com.sise.oj.exception.DataNotFoundException;
import com.sise.oj.service.RoleService;
import com.sise.oj.service.UserAuthService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Cijee
 * @version 1.0
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAuthService userAuthService;
    private final RoleService roleService;

    public UserDetailsServiceImpl(UserAuthService userAuthService, RoleService roleService) {
        this.userAuthService = userAuthService;
        this.roleService = roleService;
    }

    @Override
    public JwtUserDto loadUserByUsername(String username) throws UsernameNotFoundException {
        JwtUserDto jwtUserDto;
        UserAuth user;
        try {
            user = userAuthService.findByName(username);
        } catch (DataNotFoundException e) {
            // SpringSecurity会自动转换UsernameNotFoundException为BadCredentialsException
            throw new UsernameNotFoundException("", e);
        }
        if (user == null) {
            throw new UsernameNotFoundException("");
        } else {
            if (user.getLocked()) {
                throw new BadRequestException("用户被锁定！");
            }
            jwtUserDto = new JwtUserDto(user, roleService.mapToGrantedAuthorities(user));
        }
        return jwtUserDto;
    }
}
