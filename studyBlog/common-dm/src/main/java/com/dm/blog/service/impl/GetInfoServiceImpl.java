package com.dm.blog.service.impl;


import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.entity.User;
import com.dm.blog.domain.vo.AdminUserInfoVo;
import com.dm.blog.domain.vo.UserInfoVo;
import com.dm.blog.service.MenuService;
import com.dm.blog.service.RoleService;
import com.dm.blog.service.GetInfoService;
import com.dm.blog.utils.BeanCopyUtils;
import com.dm.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetInfoServiceImpl implements GetInfoService {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @Override
    public ResponseResult getInfo() {
        //获取当前登录的用户id
        Long id = SecurityUtils.getUserId();

        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(id);

        //根据用户id查询角色信息
        List<String> roles = roleService.selectRolesByUserId(id);

        //获取用户信息
        User user = SecurityUtils.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        //封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roles, userInfoVo);

        return ResponseResult.okResult(adminUserInfoVo);
    }
}
