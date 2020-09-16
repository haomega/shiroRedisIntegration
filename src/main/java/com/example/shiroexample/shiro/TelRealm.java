package com.example.shiroexample.shiro;

import com.example.shiroexample.service.TelCodeService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author xdnb092
 */
public class TelRealm extends AuthorizingRealm {
    private TelCodeService telCodeService;

    public TelRealm(TelCodeService telCodeService) {
        this.telCodeService = telCodeService;
        this.setCredentialsMatcher(new CredentialsMatcher() {
            @Override
            public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
                return token.getCredentials().equals(info.getCredentials());
            }
        });
    }

    @Override
    public String getName() {
        return "telRealm";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof TelCodeAuthToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String tel = (String) token.getPrincipal();
        String correctCode = telCodeService.getTelCode(tel);

        return new SimpleAuthenticationInfo(tel, correctCode, getName());
    }
}
