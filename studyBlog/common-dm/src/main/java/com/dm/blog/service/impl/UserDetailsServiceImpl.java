package com.dm.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dm.blog.constants.SystemConstants;
import com.dm.blog.domain.entity.LoginUser;
import com.dm.blog.domain.entity.User;
import com.dm.blog.mapper.MenuMapper;
import com.dm.blog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper();
        wrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(wrapper);

        //判断是否查到用户如果没查到抛出异常
        if (ObjectUtils.isEmpty(user)) {
            throw new RuntimeException("用户不存在");
        }

        List<String> perms = null;
        //查询权限信息封装，如果用户type为1则为管理员
        if (user.getType().equals(SystemConstants.ADMAIN)) {
            perms = menuMapper.selectPermsByUserId(user.getId());
        }
        //返回用户信息
        return new LoginUser(user,perms);
    }
}
