package com.sise.oj.service;

import com.sise.oj.domain.UserAuth;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
public interface RoleService {

    List<GrantedAuthority> mapToGrantedAuthorities(UserAuth userAuth);

}
