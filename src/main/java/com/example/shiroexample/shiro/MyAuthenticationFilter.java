package com.example.shiroexample.shiro;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author xdnb092
 */
public class MyAuthenticationFilter extends FormAuthenticationFilter {


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                //allow them to see the login page ;)
                return true;
            }
        } else {
            return false;
        }
    }
}
