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
public class ResourceService {

    private RoleRepository roleRepository;
    private UserRoleRepository userRoleRepository;
    private ResourceRepository resourceRepository;

    public ResourceService(RoleRepository roleRepository,
                           UserRoleRepository userRoleRepository,
                           ResourceRepository resourceRepository) {
        this.roleRepository = roleRepository;
        this.resourceRepository = resourceRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public List<Role> findUserRoles(Long userId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        List<UserRole> userRoles = userRoleRepository.findAll(Example.of(userRole));
        Set<Long> roleIds = new HashSet<>();
        for (UserRole u : userRoles) {
            if (u.getRoleId() != null) {
                roleIds.add(u.getRoleId());
            }
        }
        return roleRepository.findAllById(roleIds);
    }

    public List<Resource> findUserResource(Long userId) {

        return resourceRepository.listUserResources(userId);

    }
}
