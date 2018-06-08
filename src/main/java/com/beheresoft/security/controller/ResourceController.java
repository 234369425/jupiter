package com.beheresoft.security.controller;

import com.beheresoft.security.result.Result;
import com.beheresoft.security.service.ResourceService;
import com.beheresoft.security.util.AppUtils;
import com.beheresoft.security.util.ResourceTreeUtils;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aladi
 */
@RestController
@RequestMapping("/api/resource")
public class ResourceController {

    private ResourceTreeUtils resourceTreeUtils;
    private ResourceService resourceService;

    public ResourceController(ResourceTreeUtils resourceTreeUtils, ResourceService resourceService) {
        this.resourceTreeUtils = resourceTreeUtils;
        this.resourceService = resourceService;
    }

    @RequestMapping("/load.json ")
    public Result load() {
        resourceService.findUserResource(AppUtils.getCurrentUserId());
        return Result.ok();
    }


}
