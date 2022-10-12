package com.dm.blog.service.impl;

import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.entity.LoginUser;
import com.dm.blog.domain.entity.User;
import com.dm.blog.domain.vo.BlogUserLoginVo;
import com.dm.blog.domain.vo.UserInfoVo;
import com.dm.blog.service.BlogLoginService;
import com.dm.blog.utils.BeanCopyUtils;
import com.dm.blog.utils.JwtUtil;
import com.dm.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

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
        redisCache.setCacheObject("front-login-" + id, loginUser);

        //把User转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);

        //把token和userinfo封装返回
        BlogUserLoginVo vo = new BlogUserLoginVo(token, userInfoVo);

        return ResponseResult.okResult(vo);
    }


}
