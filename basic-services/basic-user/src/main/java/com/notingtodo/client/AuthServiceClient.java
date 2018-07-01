package com.notingtodo.client;

import com.notingtodo.client.hystrix.AuthServiceHystrix;
import com.notingtodo.entity.JWT;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 远程调用授权服务
 * Created by qilin.liu on 2018/6/30.
 */
@FeignClient(value = "basic-auth", fallback = AuthServiceHystrix.class)
public interface AuthServiceClient {

    /*
    请求授权服务 使用 post 方法
     */
    @PostMapping(value = "/oauth/token")
    JWT getToken(@RequestHeader(value = "Authorization") String authorization,
                 @RequestParam("grant_type") String type,
                 @RequestParam("username") String username,
                 @RequestParam("password") String password);
}