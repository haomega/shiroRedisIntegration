package com.example.shiroexample.shiro;

import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

public class MyAuthentizing extends ModularRealmAuthorizer {
    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        assertRealmsConfigured();
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) continue;
            // 只在当前Realm作用域判断权限
            if (principals.getRealmNames().contains(realm.getName())) {
                if (((Authorizer) realm).hasRole(principals, roleIdentifier)) {
                    return true;
                }
            }
        }
        return false;
    }
}
