package com.beheresoft.security.controller;

import com.beheresoft.security.pojo.Resource;
import com.beheresoft.security.result.Result;
import com.beheresoft.security.service.ResourceService;
import com.beheresoft.security.util.AppUtils;
import com.beheresoft.security.util.ResourceTreeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @RequestMapping("/load.json")
    public Result load() {
        List<Resource> userResource = resourceService.findUserResource(AppUtils.getCurrentUserId());
        ResourceTreeUtils.TreeNode tree = resourceTreeUtils.resourceTree(userResource);
        return Result.ok(tree);
    }


}
