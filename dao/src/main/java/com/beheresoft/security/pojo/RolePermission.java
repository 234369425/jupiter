package com.beheresoft.security.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Aladi on 2018/3/12.
 */
@Entity
@Data
public class RolePermission {

    @Id
    @GeneratedValue
    private Long rolePermissionId;
    private Long roleId;
    private Long permissionId;

}
