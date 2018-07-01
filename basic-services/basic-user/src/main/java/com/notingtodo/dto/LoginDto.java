package com.notingtodo.dto;

import com.notingtodo.entity.User;

/**
 * 登录数据传输对象
 * Created by qilin.liu on 2018/6/30.
 */
public class LoginDto {

    private User user;
    private String token;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}