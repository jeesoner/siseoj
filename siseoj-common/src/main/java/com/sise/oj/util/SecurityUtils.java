package com.sise.oj.util;

import cn.hutool.json.JSONObject;
import com.sise.oj.enums.ResultCode;
import com.sise.oj.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 获取Security中用户信息工具类
 *
 * @author Cijee
 * @version 1.0
 */
@Slf4j
public class SecurityUtils {

    /**
     * 获取当前用户信息，返回值为UserDetails类或其子类
     *
     * @return UserDetails
     */
    public static UserDetails getCurrentUser() {
        /*final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BadRequestException(ResultCode.UNAUTHORIZED, "当前登录状态过期");
        }
        // 找到登录信息则返回
        if (authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        throw new BadRequestException(ResultCode.UNAUTHORIZED, "找不到当前登录的信息");*/
        UserDetailsService userDetailsService = SpringContextHolder.getBean(UserDetailsService.class);
        return userDetailsService.loadUserByUsername(getCurrentUsername());
    }

    /**
     * 获取系统用户名称
     *
     * @return 系统用户名称
     */
    public static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BadRequestException(ResultCode.UNAUTHORIZED, "当前登录状态过期");
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        throw new BadRequestException(ResultCode.UNAUTHORIZED, "找不到当前登录的信息");
    }

    /**
     * 获取系统用户ID
     *
     * @return 系统用户ID
     */
    public static Long getCurrentUserId() {
        UserDetails userDetails = getCurrentUser();
        return new JSONObject(new JSONObject(userDetails).get("user")).get("id", Long.class);
    }

    /**
     * 判断是否超级管理员
     */
    public static boolean isSuperAdmin() {
        return "root".equals(getCurrentUsername());
    }
}
