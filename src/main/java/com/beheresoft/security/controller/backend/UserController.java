package com.beheresoft.security.controller.backend;

import com.beheresoft.security.pojo.User;
import com.beheresoft.security.result.Result;
import com.beheresoft.security.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aladi
 */
@RestController
@RequestMapping("/internal/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequiresRoles(value = {"superAdmin", "admin"}, logical = Logical.OR)
    @RequestMapping("/list.json")
    public Result list(Pageable p) {
        Subject subject = SecurityUtils.getSubject();
        User u = (User) subject.getPrincipal();
        User probe = new User();
        probe.setAppName(u.getAppName());
        return Result.ok(userService.list(probe, p));
    }

    @RequestMapping("/create.json")
    public Result create(User u) {
        this.userService.create(u);
        return Result.ok();
    }

    @RequestMapping("/lock.json")
    public Result lock(User u) {
        if (u.getLocked() == null || u.getUserId() == null) {
            return Result.err("error args");
        }
        this.userService.lock(u);
        return Result.ok();
    }

}
