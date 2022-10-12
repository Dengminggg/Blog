package com.dm.blog.handler.exception;

import com.dm.blog.domain.ResponseResult;
import com.dm.blog.enums.AppHttpCodeEnum;
import com.dm.blog.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 解决 GlobalExceptionHandler 会消费掉 AccessDeniedException ,导致 AccessDeniedHandler 不会被触发的问题
     *
     * @param e
     * @throws AccessDeniedException
     */
    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        throw e;
    }

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException ex) {
        //打印异常信息
        log.error("出现了异常！ {}", ex);
        //从异常对象中获取提示信息并封装返回
        return new ResponseResult(ex.getCode(), ex.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e) {
        //打印异常信息
        log.error("出现了异常！ {}", e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }
}