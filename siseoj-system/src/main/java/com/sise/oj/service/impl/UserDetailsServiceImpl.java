package com.sise.oj.service.impl;

import com.sise.oj.domain.User;
import com.sise.oj.domain.dto.JwtUserDto;
import com.sise.oj.exception.BadRequestException;
import com.sise.oj.exception.DataNotFoundException;
import com.sise.oj.service.RoleService;
import com.sise.oj.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Cijee
 * @version 1.0
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final RoleService roleService;

    public UserDetailsServiceImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public JwtUserDto loadUserByUsername(String username) throws UsernameNotFoundException {
        JwtUserDto jwtUserDto;
        User user;
        try {
            user = userService.findByName(username);
        } catch (DataNotFoundException e) {
            // SpringSecurity会自动转换UsernameNotFoundException为BadCredentialsException
            throw new UsernameNotFoundException("", e);
        }
        if (user == null) {
            throw new UsernameNotFoundException("");
        } else {
            if (!user.getEnabled()) {
                throw new BadRequestException("用户被锁定！");
            }
            jwtUserDto = new JwtUserDto(user, roleService.mapToGrantedAuthorities(user));
        }
        return jwtUserDto;
    }
}
