package com.example.shiroexample.shiro;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author xdnb092
 */
public class MyAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected String getName() {
        return super.getName();
    }

    /*
    覆盖这个方法，修改return逻辑，不保存失败的请求，redis里会产生很多无用的session
     */
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
            // do not save request
            redirectToLogin(request, response);
            return false;
        }
    }
}
