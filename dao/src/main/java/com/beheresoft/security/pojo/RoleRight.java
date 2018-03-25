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
public class RoleRight {

    @Id
    @GeneratedValue
    private Long roleRightId;
    private Long roleId;
    private Long rightId;

}
