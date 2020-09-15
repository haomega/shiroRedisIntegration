package com.example.shiroexample.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HelloWorldController {

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


}
