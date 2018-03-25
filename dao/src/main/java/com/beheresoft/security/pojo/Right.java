package com.beheresoft.security.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Aladi on 2018/3/12.
 */
@Entity
public class Right {

    @Id
    @GeneratedValue
    private Long rightId;

    private String key;
    private String name;
    private String uri;

}
