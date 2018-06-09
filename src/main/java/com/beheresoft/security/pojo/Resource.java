package com.beheresoft.security.pojo;

import com.beheresoft.security.enums.ResourceType;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resourceId;
    private Long parentId;
    private ResourceType type;
    private String appName;
    private String permKey;
    private String name;
    private String icon;
    private String uri;
    private Boolean available = Boolean.FALSE;

}
