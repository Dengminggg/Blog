package com.dm.blog.service;

import com.dm.blog.domain.ResponseResult;

public interface GetRoutersService {

    /**
     * 获取用户能够路由的板块
     */
    ResponseResult getRouters();

}
