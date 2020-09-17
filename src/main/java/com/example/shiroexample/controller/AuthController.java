package com.example.shiroexample.controller;

import com.example.shiroexample.shiro.TelCodeAuthToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/auth/401")
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Object noAuth() {
        Map<String, String> responseJson = new HashMap<>();
        responseJson.put("msg", "no auth");
        return responseJson;
    }

    @GetMapping("/auth/403")
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Object unAuthenrizer() {
        Map<String, String> responseJson = new HashMap<>();
        responseJson.put("msg", "request forbidden, not have Auth");
        return responseJson;
    }

}
