package com.example.shiroexample.shiro.realm;

import com.example.shiroexample.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    private final UserService userService;

    public UserRealm(UserService userService) {
        this.userService = userService;
        this.setCredentialsMatcher(new CredentialsMatcher() {
            @Override
            public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
                return new String((char[])token.getCredentials()).equals(info.getCredentials());
            }
        });
    }

    @Override
    public String getName() {
        return "shiroRealm";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals.getRealmNames().contains(getName())) {

            String username = (String) principals.getPrimaryPrincipal();

            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            Set<String> roleSet = new HashSet<>();
            if (username.equals("lisi")) {
                roleSet.add("admin");
            }
            if (username.equals("wangwu")) {
                roleSet.add("leader");
            }
            info.setRoles(roleSet);

            return info;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();

        String sysPassword = userService.getUserByName(username);
        if (sysPassword == null) {
            throw new AuthenticationException("用户不存在");
        }

        return new SimpleAuthenticationInfo(username, sysPassword, getName());
    }


}
