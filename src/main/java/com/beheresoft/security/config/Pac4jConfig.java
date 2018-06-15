package com.beheresoft.security.config;

import com.beheresoft.security.realm.CasRealm;
import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.realm.Pac4jRealm;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.core.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aladi
 * @date 2018-06-14 16:26:19
 */
@Configuration
public class Pac4jConfig {

    @Bean
    public CasConfiguration casConfiguration() {
        CasConfiguration casConfiguration = new CasConfiguration();

        return casConfiguration;
    }

    @Bean
    public Config config() {
        return new Config();
    }

    @Bean
    public CallbackFilter callbackFilter(Config config) {
        CallbackFilter callbackFilter = new CallbackFilter();
        callbackFilter.setConfig(config);
        return new CallbackFilter();
    }

}
