package com.beheresoft.security.util;

import com.beheresoft.security.config.SystemConfig;
import com.beheresoft.security.pojo.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

/**
 * @author Aladi
 * @date 2018-06-06 23:30:07
 */
@Component
public class PasswordUtils {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private SystemConfig systemConfig;

    public PasswordUtils(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void encryptPassword(User u) {
        u.setSalt(randomNumberGenerator.nextBytes().toHex());
        u.setPassword(hashPassword(u));
    }

    public String hashPassword(User u) {
        return new SimpleHash(systemConfig.getAlgorithmName(), u.getPassword(),
                ByteSource.Util.bytes(u.getCredentialsSalt()), systemConfig.getHashIterations()).toHex();
    }

}
