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

    public List<Role> findUserRoles(User user) {
        UserRolePermission userRolePermission = new UserRolePermission();
        userRolePermission.setUserId(user.getUserId());
        Example<UserRolePermission> example = Example.of(userRolePermission);
        List<UserRolePermission> rolePermissions = userRolePermissionRepository.findAll(example);
        Set<Long> roleIds = new HashSet<>();
        for (UserRolePermission u : rolePermissions) {
            if (u.getRoleId() != null) {
                roleIds.add(u.getRoleId());
            }
        }
        return roleRepository.findAllById(roleIds);
    }

    public List<Permission> findUserPermission(User user) {
        UserRolePermission userRolePermission = new UserRolePermission();
        userRolePermission.setUserId(user.getUserId());
        Example<UserRolePermission> example = Example.of(userRolePermission);
        List<UserRolePermission> userRolePermissions = userRolePermissionRepository.findAll(example);

        Set<Long> roleIds = new HashSet<>();
        Set<Long> permissionIds = new HashSet<>();

        for (UserRolePermission u : userRolePermissions) {
            if (u.getRoleId() != null) {
                roleIds.add(u.getRoleId());
            } else if (u.getPermissionId() != null) {
                permissionIds.add(u.getPermissionId());
            }
        }

        List<RolePermission> rolePermissions = new ArrayList<>();

        for (Long roleId : roleIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermissions.addAll(rolePermissionRepository.findAll(Example.of(rolePermission)));
        }

        for (RolePermission rolePermission : rolePermissions) {
            permissionIds.add(rolePermission.getPermissionId());
        }

        return permissionRepository.findAllById(permissionIds);

    }
}
