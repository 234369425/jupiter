package com.beheresoft.security.autoconfigure.redisson;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "redisson")
class RedissonProperties {

    private String enable;
    private String password;

    private String address;
    private int database = 0;
    private int timeout = 3000;
    private int poolSize = 2;
    private int minIdleSize = 1;

    private String masterName;
    private String[] sentinelAddress;
    private int masterPoolSize;
    private int masterMinIdleSize;
    private int slavePoolSize;
    private int slaveMinIdleSize;
}
