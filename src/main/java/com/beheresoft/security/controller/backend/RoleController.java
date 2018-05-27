package com.beheresoft.security.controller.backend;

import com.beheresoft.security.pojo.Role;
import com.beheresoft.security.result.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aladi
 */
@RestController
@RequestMapping("/internal/role")
public class RoleController {

    @RequestMapping("/create.json")
    public Result create(Role role) {
        return Result.ok();
    }

}
