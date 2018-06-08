package com.beheresoft.security.service;

import com.beheresoft.security.pojo.*;
import com.beheresoft.security.repository.ResourceRepository;
import com.beheresoft.security.repository.RoleResourceRepository;
import com.beheresoft.security.repository.RoleRepository;
import com.beheresoft.security.repository.UserRoleRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Aladi
 * @date 2018-06-06 23:31:26
 */
@Service
public class PermissionService {

    private RoleRepository roleRepository;
    private UserRoleRepository userRoleRepository;
    private RoleResourceRepository roleResourceRepository;
    private ResourceRepository resourceRepository;

    public PermissionService(RoleRepository roleRepository,
                             RoleResourceRepository roleResourceRepository,
                             UserRoleRepository userRoleRepository,
                             ResourceRepository resourceRepository) {
        this.roleRepository = roleRepository;
        this.roleResourceRepository = roleResourceRepository;
        this.resourceRepository = resourceRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public List<Role> findUserRoles(Long userId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        List<UserRole> rolePermissions = userRoleRepository.findAll(Example.of(userRole));
        Set<Long> roleIds = new HashSet<>();
        for (UserRole u : rolePermissions) {
            if (u.getRoleId() != null) {
                roleIds.add(u.getRoleId());
            }
        }
        return roleRepository.findAllById(roleIds);
    }

    public List<Resource> findUserResource(Long userId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        Example<UserRole> example = Example.of(userRole);
        List<UserRole> userRoles = userRoleRepository.findAll(example);

        Set<Long> roleIds = new HashSet<>();
        Set<Long> resourceIds = new HashSet<>();

        for (UserRole u : userRoles) {
            if (u.getRoleId() != null) {
                roleIds.add(u.getRoleId());
            }
        }

        List<RoleResource> roleResources = new ArrayList<>();

        for (Long roleId : roleIds) {
            RoleResource roleResource = new RoleResource();
            roleResource.setRoleId(roleId);
            roleResources.addAll(roleResourceRepository.findAll(Example.of(roleResource)));
        }

        for (RoleResource roleResource : roleResources) {
            resourceIds.add(roleResource.getResourceId());
        }

        return resourceRepository.findAllById(resourceIds);

    }
}
