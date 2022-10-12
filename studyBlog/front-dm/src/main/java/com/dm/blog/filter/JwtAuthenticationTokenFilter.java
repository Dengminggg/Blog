package com.dm.blog.filter;


import com.alibaba.fastjson.JSON;
import com.dm.blog.domain.ResponseResult;
import com.dm.blog.domain.entity.LoginUser;
import com.dm.blog.enums.AppHttpCodeEnum;
import com.dm.blog.utils.JwtUtil;
import com.dm.blog.utils.RedisCache;
import com.dm.blog.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义JWT认证过滤器
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取请求中的Token
        String token = httpServletRequest.getHeader("token");

        //如果请求头中带有token,则是需要进行身份确认,没有则是直接放行
        if (StringUtils.hasText(token)) {

            Claims jwt = null;
            try {
                jwt = JwtUtil.parseJWT(token);
            } catch (Exception e) {
                e.printStackTrace();
                //token超时  token非法
                //响应json告诉前端需要重新登录
                ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
                WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
                return;
            }

            //获取id
            String id = jwt.getSubject();
            //根据id从redis中获取用户信息
            LoginUser loginUser = redisCache.getCacheObject("front-login-" + id);
            //如果redis获取失败
            if (ObjectUtils.isEmpty(loginUser)) {
                //说明登录过期,提示重新登录
                ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
                WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
                return;
            }

            //将loginUser存入SecurityContextHolder中
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        //放行过滤器链
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
