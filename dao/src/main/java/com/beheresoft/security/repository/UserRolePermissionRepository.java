package com.beheresoft.security.repository;

import com.beheresoft.security.pojo.UserRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Aladi on 2018/3/12.
 */
public interface UserRolePermissionRepository extends JpaRepository<UserRolePermission, Long> {

}
