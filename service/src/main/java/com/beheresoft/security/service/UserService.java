package com.beheresoft.security.service;

import com.beheresoft.security.pojo.User;
import com.beheresoft.security.pojo.UserRolePermission;
import com.beheresoft.security.repository.RoleRepository;
import com.beheresoft.security.repository.UserRepository;
import com.beheresoft.security.repository.UserRolePermissionRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Aladi on 2018/3/24.
 */
@Service
public class UserService {

    private UserRepository userRepository;
    private UserRolePermissionRepository userRolePermissionRepository;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       UserRolePermissionRepository userRolePermissionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRolePermissionRepository = userRolePermissionRepository;
    }

    public User findByLoginName(String loginName) {
        return this.userRepository.findOneByLoginName(loginName);
    }

    public List<String> findUserRoles(User user) {
        UserRolePermission userRolePermission = new UserRolePermission();
        userRolePermission.setUserId(user.getUserId());
        Example<UserRolePermission> example = Example.of(userRolePermission);
        List<UserRolePermission> roles = userRolePermissionRepository.findAll(example);

        roleRepository.findAllById()
        return roles;
    }


}
