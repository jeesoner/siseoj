package com.sise.oj.config;

import com.sise.oj.util.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义EL表达式
 *
 * @author Cijee
 * @version 1.0
 */
@Service(value = "el")
public class ElPermissionConfig {

    public Boolean check(String ...permissions) {
        // 获取当前用户的所有权限
        List<String> elPermissions = SecurityUtils.getCurrentUser().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        System.out.println("当前用户的所有权限: " + SecurityUtils.getCurrentUsername() + elPermissions.toString());
        // 判断当前用户的所有权限是否包含接口上定义的权限
        return elPermissions.contains("root") || Arrays.stream(permissions).anyMatch(elPermissions::contains);
    }
}
