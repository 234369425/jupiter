package com.beheresoft.security.autoconfigure.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnClass(Redisson.class)
@ConditionalOnMissingBean(RedissonClient.class)
class RedissonClientConfiguration {


    @Configuration
    @ConditionalOnSingleCandidate(RedissonProperties.class)
    static class RedissonClientConfigConfiguration {

        @Bean
        public RedissonClient redissonClient(RedissonProperties properties) {
            return new RedissonClientFactory(properties).getRedissonClientInstance();
        }

    }

}
