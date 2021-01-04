package com.sise.oj.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.sise.oj.domain.UserAuth;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @author Cijee
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public class JwtUserDto implements UserDetails {

    private final UserAuth userAuth;

    private final List<GrantedAuthority> authorities;

    @Override
    @JSONField(serialize = false)
    public String getUsername() {
        return userAuth.getUsername();
    }

    @Override
    @JSONField(serialize = false)
    public String getPassword() {
        return userAuth.getPassword();
    }

    @Override
    @JSONField(serialize = false)
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isAccountNonLocked() {
        return userAuth.getLocked();
    }

    @Override
    @JSONField(serialize = false)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isEnabled() {
        return true;
    }
}
