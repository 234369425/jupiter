package com.beheresoft.security.controller;


import com.beheresoft.security.result.Result;
import com.beheresoft.security.token.LoginToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Aladi
 */
@RestController
@RequestMapping("/api/main")
@Validated
public class LogController {

    @RequestMapping("/{appId}/login.json")
    public Result login(@PathVariable("appId") @NotNull String appId, @NotBlank String username, @NotBlank String password) {
        Subject user = SecurityUtils.getSubject();
        if (!user.isAuthenticated()) {
            LoginToken token = new LoginToken(appId, username, password);
            user.login(token);
        }
        return Result.ok();
    }

    @RequestMapping("/logout.json")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.ok();
    }

}
