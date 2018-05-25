package com.beheresoft.security.config;

import com.beheresoft.security.cache.SpringCacheManager;
import com.beheresoft.security.credentials.RetryLimitHashedCredentialsMatcher;
import com.beheresoft.security.realm.UserRealm;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Aladi
 */
@Configuration
@ConfigurationProperties("shiro")
@Slf4j
public class ShiroConfig {

    private SystemConfig systemConfig;
    private SpringCacheManager cacheManager;

    public ShiroConfig(SystemConfig systemConfig, SpringCacheManager cacheManager) {
        this.systemConfig = systemConfig;
        this.cacheManager = cacheManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilters(ShiroConfig shiroConfig, SecurityManager securityManager, Gson gson) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        filterFactoryBean.setFilterChainDefinitionMap(gson.fromJson(shiroConfig.systemConfig.getFilterChain(), Map.class));
        filterFactoryBean.setLoginUrl(shiroConfig.systemConfig.getLoginUrl());
        filterFactoryBean.setUnauthorizedUrl(shiroConfig.systemConfig.getUnauthorizedUrl());
        return filterFactoryBean;
    }

    @Bean
    public CacheManager cacheManager() {
        return cacheManager;
    }

    @Bean
    public DefaultSecurityManager securityManager(UserRealm userRealm, HashedCredentialsMatcher hashedCredentialsMatcher) {
        DefaultSecurityManager securityManager = new DefaultWebSecurityManager();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(RetryLimitHashedCredentialsMatcher hashedCredentialsMatcher) {
        hashedCredentialsMatcher.setHashAlgorithmName(systemConfig.getAlgorithmName());
        hashedCredentialsMatcher.setHashIterations(systemConfig.getHashIterations());
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

}
