package com.dm.blog.service.impl;

import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.entity.LoginUser;
import com.dm.blog.domain.entity.User;
import com.dm.blog.service.AdminLoginService;
import com.dm.blog.utils.JwtUtil;
import com.dm.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        //进行查询得到authentication认证对象
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authentication);

        //判断是否认证通过
        if (ObjectUtils.isEmpty(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }

        //获取userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        String token = JwtUtil.createJWT(id);

        //把用户信息存入redis
        redisCache.setCacheObject("admin-login-" + id, loginUser);

        //把token封装 返回
        Map<String, String> map = new HashMap<>();
        map.put("token", token);

        return ResponseResult.okResult(map);
    }
}
