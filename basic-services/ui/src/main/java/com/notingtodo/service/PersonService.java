package com.notingtodo.service;

import com.notingtodo.domain.Person;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 只需通过简单的接口声明方法即可调用Person服务的Rest服务。
 * Created by qilin.liu on 2018/6/19.
 */
@FeignClient("person")
public interface PersonService {
    @RequestMapping(method = RequestMethod.POST,
            value = "/save", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    List<Person> save(@RequestBody String name);
}
