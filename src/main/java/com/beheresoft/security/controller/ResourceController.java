package com.beheresoft.security.controller;

import com.beheresoft.security.result.Result;
import com.beheresoft.security.util.AppUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aladi
 */
@RestController
@RequestMapping("/api/resource")
public class ResourceController {

    public ResourceController(){

    }

    @RequestMapping("/load.json ")
    public Result load() {
        String appName = AppUtils.getCurrentUserAppName();
        return Result.ok();
    }


}
