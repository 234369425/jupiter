package com.beheresoft.security.realm;

import com.beheresoft.security.pojo.Permission;
import com.beheresoft.security.pojo.Role;
import com.beheresoft.security.pojo.User;
import com.beheresoft.security.service.PermissionService;
import com.beheresoft.security.service.UserService;
import com.beheresoft.security.token.LoginToken;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Aladi on 2018/3/24.
 *
 * @author Aladi
 */
@Component
public class LoginRealm extends AuthorizingRealm {

    private UserService userService;
    private PermissionService permissionService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null && token instanceof LoginToken;
    }

    public LoginRealm(UserService userService, PermissionService permissionService) {
        this.userService = userService;
        this.permissionService = permissionService;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Long userId = Long.parseLong(principalCollection.toString());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<Role> roles = permissionService.findUserRoles(userId);
        for (Role role : roles) {
            authorizationInfo.addRole(role.getName());
        }
        List<Permission> permissions = permissionService.findUserPermission(userId);
        for (Permission permission : permissions) {
            authorizationInfo.addStringPermission(permission.getKey());
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        LoginToken token = (LoginToken) authenticationToken;
        User user = userService.findUser(token.getAppId(), token.getUsername());
        if (user == null) {
            throw new UnknownAccountException();
        }
        if (user.getLocked() == null || user.getLocked()) {
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUserId(),
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                getName());
        return authenticationInfo;
    }

}
