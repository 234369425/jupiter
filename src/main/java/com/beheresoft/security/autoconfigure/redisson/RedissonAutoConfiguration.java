package com.beheresoft.security.autoconfigure.redisson;

import org.redisson.Redisson;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnProperty(name = "redisson.enable", havingValue = "true")
@EnableConfigurationProperties(RedissonProperties.class)
@ConditionalOnClass(Redisson.class)
@Import({RedissonClientConfiguration.class})
public class RedissonAutoConfiguration {

}

