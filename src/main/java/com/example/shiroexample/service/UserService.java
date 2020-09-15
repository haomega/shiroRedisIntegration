package com.example.shiroexample.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String getUserByName(String username) {
        if ("lisi".equals(username)) {
            return "lisi";
        } else if ("wangwu".equals(username)) {
            return "wangwu";
        } else {
            return null;
        }
    }
}
