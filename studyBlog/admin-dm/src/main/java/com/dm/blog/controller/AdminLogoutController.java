package com.dm.blog.controller;


import com.dm.blog.domain.ResponseResult;
import com.dm.blog.service.AdminLoginService;
import com.dm.blog.service.AdminLogoutService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminLogoutController {

    @Autowired
    private AdminLogoutService adminLogoutService;

    @ApiOperation("退出登录状态")
    @PostMapping("/user/logout")
    public ResponseResult logout() {
        return adminLogoutService.logout();
    }
}
