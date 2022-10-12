package com.dm.blog.controller;

import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.entity.User;
import com.dm.blog.enums.AppHttpCodeEnum;
import com.dm.blog.exception.SystemException;
import com.dm.blog.service.AdminLoginService;
import com.dm.blog.service.GetInfoService;
import com.dm.blog.service.GetRoutersService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminLoginController {
    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private GetInfoService getInfoService;

    @Autowired
    private GetRoutersService getRoutersService;

    @ApiOperation("后台登录验证")
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return adminLoginService.login(user);
    }

    @ApiOperation("获取用户权限")
    @GetMapping("/getInfo")
    public ResponseResult getInfo() {
        return getInfoService.getInfo();
    }

    @ApiOperation("获取用户能够路由板块")
    @GetMapping("/getRouters")
    public ResponseResult getRouters() {
        return getRoutersService.getRouters();
    }
}