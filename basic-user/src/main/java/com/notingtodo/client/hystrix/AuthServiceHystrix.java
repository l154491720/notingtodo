package com.notingtodo.client.hystrix;

import com.notingtodo.client.AuthServiceClient;
import com.notingtodo.entity.JWT;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by qilin.liu on 2018/6/30.
 */
@Component
public class AuthServiceHystrix implements AuthServiceClient{

    /**
     * getToken的断容器
     */
    @Override
    public JWT getToken(@RequestHeader(value = "Authorization") String authorization, @RequestParam("grant_type") String type, @RequestParam("username") String username, @RequestParam("password") String password) {
        System.out.println("--------opps getToken hystrix---------");
        return null;
    }
}