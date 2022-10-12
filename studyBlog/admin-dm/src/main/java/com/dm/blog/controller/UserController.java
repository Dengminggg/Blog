package com.dm.blog.controller;


import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.dto.UserDto;
import com.dm.blog.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("查询用户列表")
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, UserDto userDto) {
        return userService.list(pageNum, pageSize, userDto);
    }

    @ApiOperation("新增用户")
    @PostMapping
    public ResponseResult addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/{userIds}")
    public ResponseResult deleteUser(@PathVariable List<Long> userIds) {
        return userService.deleteUsers(userIds);
    }

    @ApiOperation("根据id查询用户信息回显")
    @GetMapping("/{userId}")
    public ResponseResult getUserInfoAndRoleIds(@PathVariable("userId") Long userId) {
        return userService.getUserInfoAndRoleIds(userId);
    }

    @ApiOperation("更新用户信息")
    @PutMapping
    public ResponseResult updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }
}
