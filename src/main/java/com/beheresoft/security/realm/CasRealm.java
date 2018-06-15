package com.beheresoft.security.realm;

import com.beheresoft.security.service.LoginService;
import com.beheresoft.security.token.CasToken;
import io.buji.pac4j.realm.Pac4jRealm;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.stereotype.Component;

/**
 * @author Aladi
 * @date 2018-06-14 15:48:49
 */
@Component
@Slf4j
@EqualsAndHashCode
public class CasRealm extends Pac4jRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        boolean supports = token != null && token instanceof CasToken
                && CasToken.class.getName().equals(token.getClass().getName());
        log.debug("{} supports {}", CasRealm.class.getSimpleName(), supports);
        return supports;
    }

    private LoginService loginService;

    public CasRealm(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return loginService.doGetAuthorizationInfo(principals);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        CasToken casToken = (CasToken) authenticationToken;
        return loginService.doGetAuthenticationInfo(casToken.getAppId(), casToken.getUsername()
                , getName());
    }
}
