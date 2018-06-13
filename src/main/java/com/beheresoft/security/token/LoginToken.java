package com.beheresoft.security.token;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 * @author Aladi
 */
@Getter
@Setter
@NoArgsConstructor
public class LoginToken implements HostAuthenticationToken, RememberMeAuthenticationToken {

    private String username;
    private String appId;
    private char[] password;
    private boolean rememberMe;
    private String host;

    public LoginToken(String appId, String username, String password) {
        this.appId = appId;
        this.username = username;
        this.password = password == null ? null : password.toCharArray();
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public boolean isRememberMe() {
        return rememberMe;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }
}
