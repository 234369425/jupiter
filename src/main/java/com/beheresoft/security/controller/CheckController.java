package com.beheresoft.security.controller;

import com.beheresoft.security.result.Result;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aladi
 */
@RestController
@RequestMapping("/api/check/{appId}")
public class CheckController {

    @RequestMapping("/role/{role}")
    public Result check(@PathVariable("appId") String appId, @PathVariable("role") String role) {
        SecurityUtils.getSubject().checkRole(role);
        return Result.ok();
    }

    @RequestMapping("/permission/{permission}")
    public Result checkPermissions(@PathVariable("appId") String appId, @PathVariable("permission") String permission) {
        SecurityUtils.getSubject().checkPermission(permission);
        return Result.ok();
    }

}
