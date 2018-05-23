package com.beheresoft.security.config;

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

}
