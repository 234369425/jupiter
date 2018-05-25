package com.beheresoft.security.handler;

import com.beheresoft.security.result.Result;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Aladi
 */
@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(value = {UnknownAccountException.class, IncorrectCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result exception() {
        return Result.err("用户名或密码错误!");
    }

    @ExceptionHandler(value = {LockedAccountException.class})
    public Result lockedException() {
        return Result.err("用户名被锁定!");
    }

    @ExceptionHandler(value = {AuthorizationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result authorizationException() {
        return Result.err("无权限");
    }

}
