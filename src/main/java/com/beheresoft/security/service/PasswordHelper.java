package com.beheresoft.security.service;

import com.beheresoft.security.pojo.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

/**
 * @author Aladi
 */
@Component
public class PasswordHelper {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private String algorithmName = "md5";

    private int hashIterations = 2;

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void encryptPassword(User u) {
        u.setSalt(randomNumberGenerator.nextBytes().toHex());
        u.setPassword(new SimpleHash(algorithmName, u.getPassword(),
                ByteSource.Util.bytes(u.getCredentialsSalt()), hashIterations).toHex());
    }

}
