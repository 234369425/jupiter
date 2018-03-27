package com.beheresoft.security.controller;

import com.beheresoft.security.realm.UserRealm;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserRealm userRealm;

    public UserController(UserRealm userRealm) {
        this.userRealm = userRealm;
    }
}
