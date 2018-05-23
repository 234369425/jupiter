package com.beheresoft.security.config;

import com.beheresoft.security.realm.UserRealm;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author Aladi
 */
@Configuration
@ConfigurationProperties("shiro")
@Slf4j
public class ShiroConfig {

    private SystemConfig systemConfig;

    public ShiroConfig(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilters(ShiroConfig shiroConfig, SecurityManager securityManager, Gson gson) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        filterFactoryBean.setFilterChainDefinitionMap(gson.fromJson(shiroConfig.systemConfig.getFilterChain(),Map.class));
        filterFactoryBean.setLoginUrl(shiroConfig.systemConfig.getLoginUrl());
        filterFactoryBean.setUnauthorizedUrl(shiroConfig.systemConfig.getUnauthorizedUrl());
        return filterFactoryBean;
    }

    @Bean
    public DefaultSecurityManager securityManager(UserRealm userRealm) {
        DefaultSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

}
