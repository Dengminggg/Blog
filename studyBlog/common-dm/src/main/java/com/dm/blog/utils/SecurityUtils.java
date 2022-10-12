package com.dm.blog.utils;

import com.dm.blog.domain.entity.LoginUser;
import com.dm.blog.domain.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * SpringSecurity工具类
 */
public class SecurityUtils {

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    public static User getUser() {
        return getLoginUser().getUser();
    }

    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }

    /**
     * 检验是否为管理员
     *
     * @return
     */
    public static Boolean isAdmin() {
        Long id = getLoginUser().getUser().getId();
        return id != null && 1L == id;
    }
}