package com.beheresoft.security.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Aladi on 2018/3/12.
 *
 * @author Aladi
 */
@Entity
@Data
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolePermissionId;
    private Long roleId;
    private Long permissionId;

}
