package com.beheresoft.security.autoconfigure.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.util.StringUtils;

class RedissonClientFactory {

    private final RedissonProperties properties;

    RedissonClientFactory(RedissonProperties config) {
        this.properties = config;
    }

    RedissonClient getRedissonClientInstance() {
        Config config = new Config();
        if (StringUtils.hasText(properties.getAddress())) {
            SingleServerConfig singleServerConfig = config.useSingleServer().setAddress(properties.getAddress())
                    .setDatabase(properties.getDatabase())
                    .setTimeout(properties.getTimeout())
                    .setConnectionPoolSize(properties.getPoolSize())
                    .setConnectionMinimumIdleSize(properties.getMinIdleSize());
            if (StringUtils.hasText(properties.getPassword())) {
                singleServerConfig.setPassword(properties.getPassword());
            }
        } else if (StringUtils.hasText(properties.getMasterName())) {
            SentinelServersConfig serversConfig = config.useSentinelServers()
                    .addSentinelAddress(properties.getSentinelAddress())
                    .setMasterName(properties.getMasterName())
                    .setMasterConnectionPoolSize(properties.getMasterPoolSize())
                    .setMasterConnectionMinimumIdleSize(properties.getMasterMinIdleSize())
                    .setSlaveConnectionMinimumIdleSize(properties.getSlaveMinIdleSize())
                    .setSlaveConnectionPoolSize(properties.getPoolSize())
                    .setDatabase(properties.getDatabase());
            if (StringUtils.hasText(properties.getPassword())) {
                serversConfig.setPassword(properties.getPassword());
            }
        }
        return Redisson.create(config);
    }

}
