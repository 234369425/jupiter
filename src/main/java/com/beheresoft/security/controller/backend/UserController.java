package com.beheresoft.security.controller.backend;

import com.beheresoft.security.pojo.User;
import com.beheresoft.security.result.Result;
import com.beheresoft.security.service.UserService;
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
