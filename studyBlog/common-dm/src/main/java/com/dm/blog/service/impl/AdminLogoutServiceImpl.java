package com.dm.blog.service.impl;

import com.dm.blog.domain.ResponseResult;
import com.dm.blog.service.AdminLogoutService;
import com.dm.blog.utils.RedisCache;
import com.dm.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminLogoutServiceImpl implements AdminLogoutService {

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult logout() {

        //获取useid
        Long id = SecurityUtils.getUserId();

        //从redis删除id对应的数据
        redisCache.deleteObject("admin-login-" + id);

        //返回响应对象
        return ResponseResult.okResult();
    }
}
