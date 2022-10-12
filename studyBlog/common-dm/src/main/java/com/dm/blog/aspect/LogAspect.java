package com.dm.blog.aspect;


import com.alibaba.fastjson.JSON;
import com.dm.blog.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 定义日志切入
 */
@Component
@Aspect
@Slf4j
public class LogAspect {


    //利用注解方式对切入点进行抽取
    @Pointcut("@annotation(com.dm.blog.annotation.SystemLog)")
    public void poingCut() {
    }

    //利用环绕通知
    @Around("poingCut()")
    public Object printSystemLog(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            handleBefore(joinPoint);
            Object result = joinPoint.proceed();
            handleAfter(result);
            return result;
        } finally {
            // 结束后换行
            log.info("==============End==============" + System.lineSeparator());
        }


    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {

        //通过ThreadLocal获取当前的request
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //获取被增强方法上的注解对象
        SystemLog systemLog = getAnnotation(joinPoint);

        log.info("==============Start==============");
        // 打印请求URL
        log.info("URL            : {}", request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}", systemLog.BusinessName());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), ((MethodSignature) joinPoint.getSignature()).getName());
        // 打印请求的IP
        log.info("IP             : {}", request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }


    private void handleAfter(Object result) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSONString(result));
    }

    private SystemLog getAnnotation(ProceedingJoinPoint joinPoint) {
        //获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SystemLog annotation = methodSignature.getMethod().getAnnotation(SystemLog.class);
        return annotation;
    }
}
