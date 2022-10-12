package com.dm.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.dto.UserDto;
import com.dm.blog.domain.entity.User;

import java.util.List;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-10-05 16:54:44
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult list(Integer pageNum, Integer pageSize, UserDto userDto);

    ResponseResult addUser(UserDto userDto);

    ResponseResult deleteUsers(List<Long> userIds);

    ResponseResult getUserInfoAndRoleIds(Long userId);

    ResponseResult updateUser(UserDto userDto);
}
