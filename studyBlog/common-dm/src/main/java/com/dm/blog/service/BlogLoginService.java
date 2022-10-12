package com.dm.blog.service;

import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);
}
