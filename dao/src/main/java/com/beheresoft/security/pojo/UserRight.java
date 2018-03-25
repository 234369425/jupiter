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
public class UserRight {

    @Id
    @GeneratedValue
    private Long userRightId;

    private Long userId;

    private Long rightId;

}
