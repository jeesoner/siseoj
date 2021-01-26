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
     * 获取系统用户信息
     *
     * @return UserDetails
     */
    public static UserDetails getCurrentUser() {
        UserDetailsService userDetailsService = SpringContextHolder.getBean(UserDetailsService.class);
        return userDetailsService.loadUserByUsername(getCurrentUsername());
    }

    /**
     * 获取系统用户名称
     *
     * @return 系统用户名称
     */
    private static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BadRequestException(ResultCode.INVALID_CREDENTIALS, "当前登录状态过期");
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        throw new BadRequestException(ResultCode.INVALID_CREDENTIALS, "找不到当前登录的信息");
    }

    /**
     * 获取系统用户ID
     * @return 系统用户ID
     */
    public static Long getCurrentUserId() {
        UserDetails userDetails = getCurrentUser();
        return new JSONObject(new JSONObject(userDetails).get("user")).get("id", Long.class);
    }
}
