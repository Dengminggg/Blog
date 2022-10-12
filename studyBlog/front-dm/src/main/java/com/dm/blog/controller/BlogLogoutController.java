package com.dm.blog.controller;

import com.dm.blog.annotation.SystemLog;
import com.dm.blog.domain.ResponseResult;
import com.dm.blog.service.BlogLogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLogoutController {

    @Autowired
    private BlogLogoutService blogLogoutService;

    @SystemLog(BusinessName = "退出操作")
    @PostMapping("/logout")
    public ResponseResult logout() {
        return blogLogoutService.logout();
    }
}
