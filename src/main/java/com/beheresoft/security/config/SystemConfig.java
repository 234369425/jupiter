package com.beheresoft.security.config;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author Aladi
 */
@Configuration
@ConfigurationProperties("system")
@Getter
@Setter
public class SystemConfig {

    private String filterChain;
    private String loginUrl;
    private String unauthorizedUrl;

    private String algorithmName = "md5";
    private int hashIterations = 2;

    private ShiroProperties shiro;
    private Session session;


    @Getter
    @Setter
    public static class ShiroProperties{
        private String cacheManager;
        private String credentialsMatcher;
    }

    @Getter
    @Setter
    public static class Session{
        private String cacheKeyPrefix = "JUPITER";
        private String cookieName = "JUPITER";
        private Boolean urlSessionId = Boolean.FALSE;
        private long timeout = 60 * 30;
    }

}
