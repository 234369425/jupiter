package com.beheresoft.security.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Aladi on 2018/3/12.
 */
@Entity
@Getter
@Setter
public class Permission {

    @Id
    @GeneratedValue
    private Long permissionId;
    private String key;
    private String name;
    private String uri;

}