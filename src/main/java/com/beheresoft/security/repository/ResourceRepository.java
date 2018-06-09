package com.beheresoft.security.repository;

import com.beheresoft.security.pojo.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Aladi on 2018/3/12.
 *
 * @author Aladi
 */
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    /**
     * 根据UserId查找用户拥有的资源
     * @param userId 用户Id
     * @return resource list
     */
    @Query(value = "SELECT r.* from user_role ur INNER JOIN role_resource rr " +
            "ON ur.role_id = rr.role_id INNER JOIN resource r " +
            "ON rr.resource_id = r.resource_id WHERE ur.user_id = :userId  ",nativeQuery = true)
    List<Resource> listUserResources(@Param("userId") Long userId);

}
