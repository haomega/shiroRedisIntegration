package com.example.shiroexample.controller;

import com.example.shiroexample.shiro.TelCodeAuthToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthController {

    @GetMapping("/auth/login")
    @ResponseBody
    public String login() {
        String name = "wangwu";
        String password = "wangwu";
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
//        token.setRememberMe(true);

        SecurityUtils.getSubject().login(token);
        return "success";
    }


    @GetMapping("/auth/loginphone")
    @ResponseBody
    public String loginWithTel() {
        String tel = "13027728717";
        String code = "000000";
        TelCodeAuthToken token = new TelCodeAuthToken(tel, code);

        SecurityUtils.getSubject().login(token);

        return "success";
    }


}
