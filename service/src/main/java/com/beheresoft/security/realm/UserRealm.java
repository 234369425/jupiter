package com.beheresoft.security.realm;

import com.beheresoft.security.pojo.User;
import com.beheresoft.security.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

/**
 * Created by Aladi on 2018/3/24.
 */
@Component
public class UserRealm extends AuthorizingRealm {

    private UserService userService;

    public UserRealm(UserService userService) {
        this.userService = userService;
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String loginName = (String) principalCollection.getPrimaryPrincipal();
        User user = userService.findByLoginName(loginName);
        if (user != null) {
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        }
        return null;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String loginName = (String) authenticationToken.getPrincipal();
        User user = userService.findByLoginName(loginName);
        if (user == null) {
            throw new UnknownAccountException();
        }
        if (user.getLocked()) {
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getLoginName(),
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                getName());
        return authenticationInfo;
    }

}
