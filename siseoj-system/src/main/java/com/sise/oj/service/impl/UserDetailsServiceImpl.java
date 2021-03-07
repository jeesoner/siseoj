package com.sise.oj.service.impl;

import com.sise.oj.domain.User;
import com.sise.oj.domain.dto.JwtUserDto;
import com.sise.oj.exception.BadRequestException;
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
        User user = userService.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在！");
        } else {
            if (!user.getEnabled()) {
                throw new BadRequestException("该用户已被锁定！");
            }
            jwtUserDto = new JwtUserDto(user, roleService.mapToGrantedAuthorities(user));
        }
        return jwtUserDto;
    }
}
