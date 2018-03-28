package com.beheresoft.security.realm;

import com.beheresoft.security.pojo.Permission;
import com.beheresoft.security.pojo.Role;
import com.beheresoft.security.pojo.User;
import com.beheresoft.security.service.PermissionService;
import com.beheresoft.security.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Aladi on 2018/3/24.
 */
@Component
public class UserRealm extends AuthorizingRealm {

    private UserService userService;
    private PermissionService permissionService;

    public UserRealm(UserService userService, PermissionService permissionService) {
        this.userService = userService;
        this.permissionService = permissionService;
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String loginName = (String) principalCollection.getPrimaryPrincipal();
        User user = userService.findByLoginName(loginName);
        if (user != null) {
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            List<Role> roles = permissionService.findUserRoles(user);
            for (Role role : roles) {
                authorizationInfo.addRole(role.getName());
            }
            List<Permission> permissions = permissionService.findUserPermission(user);
            for (Permission permission : permissions) {
                authorizationInfo.addStringPermission(permission.getKey());
            }
            return authorizationInfo;
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
