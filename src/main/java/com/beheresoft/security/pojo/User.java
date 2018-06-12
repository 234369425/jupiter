package com.beheresoft.security.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Aladi on 2018/3/12.
 *
 * @author Aladi
 */
@Data
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String appName;
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
