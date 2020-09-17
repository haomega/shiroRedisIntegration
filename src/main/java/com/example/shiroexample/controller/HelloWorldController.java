package com.example.shiroexample.controller;

import com.example.shiroexample.shiro.ShiroSessionManager;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {

    @Autowired
    ShiroSessionManager sessionManager;

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello, world";
    }

    @GetMapping("/home")
    @ResponseBody
    public String home() {
        return "this is my home";
    }

    @GetMapping("/admin")
    @RequiresRoles("admin")
    @ResponseBody
    public String admin() {
        return "i am admin";
    }

    @GetMapping("/online")
    @ResponseBody
    public String getOnline() {
        return sessionManager.getOnline();
    }




}
