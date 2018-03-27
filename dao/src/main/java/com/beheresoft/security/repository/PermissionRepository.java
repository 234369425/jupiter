package com.beheresoft.security.repository;

import com.beheresoft.security.pojo.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Aladi on 2018/3/12.
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
