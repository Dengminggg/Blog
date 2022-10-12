package com.dm.blog.controller;

import com.dm.blog.annotation.SystemLog;
import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.entity.User;
import com.dm.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserService userService;

    @SystemLog(BusinessName = "获取用户信息")
    @GetMapping("/userInfo")
    public ResponseResult userInfo() {
        return userService.userInfo();
    }

    @SystemLog(BusinessName = "更新用户信息")
    @PutMapping("/userInfo")
    public ResponseResult updateUserInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }

    @SystemLog(BusinessName = "用户注册")
    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user) {
        return userService.register(user);
    }
}
