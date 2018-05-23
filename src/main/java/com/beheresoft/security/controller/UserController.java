package com.beheresoft.security.controller;

import com.beheresoft.security.pojo.User;
import com.beheresoft.security.realm.UserRealm;
import com.beheresoft.security.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aladi
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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

    @RequestMapping("/create.json")
    @ResponseBody
    public Result create(User u) {
        return Result.ok(this.userService.create(u));
    }

}
