package com.example.shiroexample.controller;

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
        String name = "lisi";
        String password = "lisi";
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
//        token.setRememberMe(true);

        SecurityUtils.getSubject().login(token);
        return "success";
    }

}
