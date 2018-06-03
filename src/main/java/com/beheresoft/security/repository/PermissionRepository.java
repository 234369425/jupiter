package com.beheresoft.security.repository;

import com.beheresoft.security.pojo.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Aladi on 2018/3/12.
 * @author Aladi
 */
public interface PermissionRepository extends JpaRepository<Resource, Long> {

}
