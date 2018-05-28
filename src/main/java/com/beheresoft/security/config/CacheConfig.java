package com.beheresoft.security.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author Aladi
 */
@Configuration
public class CacheConfig {

    private ApplicationContext applicationContext;

    public CacheConfig(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Bean
    CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        String[] profiles = applicationContext.getEnvironment().getActiveProfiles();
        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("default")));
        return cacheManager;
    }

}
