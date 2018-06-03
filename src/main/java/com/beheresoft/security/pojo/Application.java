package com.beheresoft.security.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @author Aladi
 */
@Entity
@Getter
@Setter
public class Application {

    private Long id;
    private String appName;
    private String appKey;
    private String appSecret;
    private Boolean available = Boolean.FALSE;

}
