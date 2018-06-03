package com.beheresoft.security.service;

import com.beheresoft.security.pojo.*;
import com.beheresoft.security.repository.PermissionRepository;
import com.beheresoft.security.repository.RolePermissionRepository;
import com.beheresoft.security.repository.RoleRepository;
import com.beheresoft.security.repository.UserRolePermissionRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Aladi
 */
@Service
public class PermissionService {

    private UserRolePermissionRepository userRolePermissionRepository;
    private RoleRepository roleRepository;
    private RolePermissionRepository rolePermissionRepository;
    private PermissionRepository permissionRepository;

    public PermissionService(RoleRepository roleRepository,
                             RolePermissionRepository rolePermissionRepository,
                             PermissionRepository permissionRepository,
                             UserRolePermissionRepository userRolePermissionRepository) {
        this.roleRepository = roleRepository;
        this.userRolePermissionRepository = userRolePermissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
    }

    public List<Role> findUserRoles(Long userId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        Example<UserRole> example = Example.of(userRole);
        List<UserRole> rolePermissions = userRolePermissionRepository.findAll(example);
        Set<Long> roleIds = new HashSet<>();
        for (UserRole u : rolePermissions) {
            if (u.getRoleId() != null) {
                roleIds.add(u.getRoleId());
            }
        }
        return roleRepository.findAllById(roleIds);
    }

    public List<Resource> findUserPermission(Long userId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        Example<UserRole> example = Example.of(userRole);
        List<UserRole> userRoles = userRolePermissionRepository.findAll(example);

        Set<Long> roleIds = new HashSet<>();
        Set<Long> permissionIds = new HashSet<>();

        for (UserRole u : userRoles) {
            if (u.getRoleId() != null) {
                roleIds.add(u.getRoleId());
            }
        }

        List<RoleResource> roleResources = new ArrayList<>();

        for (Long roleId : roleIds) {
            RoleResource roleResource = new RoleResource();
            roleResource.setRoleId(roleId);
            roleResources.addAll(rolePermissionRepository.findAll(Example.of(roleResource)));
        }

        for (RoleResource roleResource : roleResources) {
            permissionIds.add(roleResource.getPermissionId());
        }

        return permissionRepository.findAllById(permissionIds);

    }
}
