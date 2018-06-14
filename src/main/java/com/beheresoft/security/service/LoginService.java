package com.beheresoft.security.service;

import com.beheresoft.security.pojo.Resource;
import com.beheresoft.security.pojo.Role;
import com.beheresoft.security.pojo.User;
import com.beheresoft.security.token.LoginToken;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Aladi
 */
@Component
public class LoginService {

    private UserService userService;
    private ResourceService resourceService;

    public LoginService(UserService userService, ResourceService resourceService) {
        this.userService = userService;
        this.resourceService = resourceService;
    }

    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<Role> roles = resourceService.findUserRoles(user.getUserId());
        for (Role role : roles) {
            authorizationInfo.addRole(role.getName());
        }
        List<Resource> permissions = resourceService.findUserResource(user.getUserId());
        for (Resource permission : permissions) {
            if (permission.getPermKey() == null) {
                continue;
            }
            authorizationInfo.addStringPermission(permission.getPermKey());
        }
        return authorizationInfo;
    }

    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken, String name) {
        LoginToken token = (LoginToken) authenticationToken;
        User user = userService.findUser(token.getAppId(), token.getUsername());
        if (user == null) {
            throw new UnknownAccountException();
        }
        if (user.getLocked() == null || user.getLocked()) {
            throw new LockedAccountException();
        }
        User principal = new User();
        principal.setUserId(user.getUserId());
        principal.setAppName(user.getAppName());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                principal,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                name);
        return authenticationInfo;
    }

}
