package com.beheresoft.security.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Aladi on 2018/3/12.
 * @author Aladi
 */
@Entity
@Data
public class Role {
    @Id
    @GeneratedValue
    private Long roleId;
    private String name;
    private String describ;
}
