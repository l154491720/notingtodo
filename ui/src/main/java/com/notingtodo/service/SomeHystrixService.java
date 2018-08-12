package com.notingtodo.service;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by qilin.liu on 2018/6/19.
 */
@Service
public class SomeHystrixService {

    //1 Spring boot 下使用ribbon，我们只需注入一个RestTemplate即可，Spring Boot为我们做好了配置
   @Autowired
    RestTemplate restTemplate;

    //2 使用 @HystrixCommand 的 fallbackMethod 参数指定当本方法调用失败时调用后备方法 fallbackSome
    @HystrixCommand(fallbackMethod = "fallbackSome")
    public String getSome(){
        return restTemplate.getForObject("http://some/getsome",String.class);
    }

    public String fallbackSome(){
        return "some service故障";
    }
}
