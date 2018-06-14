package com.beheresoft.security.realm;

import com.beheresoft.security.service.LoginService;
import com.beheresoft.security.token.LoginToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

/**
 * Created by Aladi on 2018/3/24.
 *
 * @author Aladi
 */
@Component
public class LoginRealm extends AuthorizingRealm {

    private LoginService loginService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null && token instanceof LoginToken
                && LoginToken.class.getName().equals(token.getClass().getName());
    }

    public LoginRealm(LoginService loginService) {
        this.loginService = loginService;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return loginService.doGetAuthorizationInfo(principalCollection);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return loginService.doGetAuthenticationInfo(authenticationToken, getName());
    }
}
