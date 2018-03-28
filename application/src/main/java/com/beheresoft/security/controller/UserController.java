package com.beheresoft.security.controller;

import com.beheresoft.security.pojo.User;
import com.beheresoft.security.realm.UserRealm;
import com.beheresoft.security.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserRealm userRealm;
    private UserService userService;

    public UserController(UserRealm userRealm, UserService userService) {
        this.userService = userService;
        this.userRealm = userRealm;
    }

    @RequestMapping("/create")
    @ResponseBody
    public Result create(User u) {
        return Result.ok(this.userService.create(u));
    }

}
