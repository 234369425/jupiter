package com.beheresoft.security.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Aladi on 2018/3/12.
 * @author Aladi
 */
@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long userId;
    private String loginName;
    private String password;
    private String salt;
    private String name;
    private String email;
    private Boolean locked;

    public String getCredentialsSalt() {
        return loginName + salt;
    }
}
