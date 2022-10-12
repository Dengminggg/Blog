package com.dm.blog.handler.security;

import com.dm.blog.utils.SecurityUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service("perm")
public class PermissionServiceImpl {

    /**
     * 判断当前用户是否具有permission
     *
     * @param permission 要判断的权限
     * @return
     */
    public boolean hasPermission(String permission) {
        //如果是admin超级管理员，直接返回true
        if (SecurityUtils.isAdmin()) {
            return true;
        } else {
            //否则获取当前登录用户所具有的权限列表 如何判断是否存在permission
            List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
            return ObjectUtils.isEmpty(permissions) ? false : permissions.contains(permission);
        }
    }
}
