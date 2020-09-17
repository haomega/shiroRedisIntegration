package com.example.shiroexample.shiro;

import org.apache.shiro.authc.AuthenticationToken;

public class TelCodeAuthToken implements AuthenticationToken {
    // 手机号
    private String tel;
    // 验证码
    private String code;

    public TelCodeAuthToken(String tel, String code) {
        this.tel = tel;
        this.code = code;
    }

    @Override
    public Object getPrincipal() {
        return tel;
    }

    @Override
    public Object getCredentials() {
        return code;
    }
}
