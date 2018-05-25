package com.beheresoft.security.controller;


import com.beheresoft.security.result.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aladi
 */
@RestController
@RequestMapping("/api/main")
public class LogController {

    @RequestMapping("/login.json")
    public Result login(String userName, String password) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return Result.err("empty userName or password");
        }
        Subject user = SecurityUtils.getSubject();
        if (!user.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
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
