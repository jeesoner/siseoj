package com.sise.oj.service.impl;

import com.sise.oj.domain.UserAuth;
import com.sise.oj.mapper.RoleMapper;
import com.sise.oj.service.RoleService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色 服务实现类
 *
 * @author Cijee
 * @version 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    /**
     * 获取用户的权限信息
     *
     * @param userAuth 用户
     * @return 权限信息列表
     */
    @Override
    public List<GrantedAuthority> mapToGrantedAuthorities(UserAuth userAuth) {
        Set<String> permissions = new HashSet<>();
        // 如果是root用户直接返回 root用户具有最高权限
        if ("root".equals(userAuth.getUsername())) {
            permissions.add("root");
            return permissions.stream().map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
        roleMapper.findByUserId(userAuth.getUserId());
        return null;
    }
}
