package com.beheresoft.security.controller;

import com.beheresoft.security.result.Result;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Aladi
 */
@RequestMapping("/api/authentication")
public class AuthenticationController {

    @RequestMapping("/get")
    public Result getAuthenticationInfo(String token) {
        return Result.ok();
    }

}
