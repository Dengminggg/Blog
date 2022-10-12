package com.dm.blog.service;

import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.entity.User;

public interface AdminLoginService {
    ResponseResult login(User user);
}
