package com.notingtodo.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.notingtodo.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qilin.liu on 2018/6/19.
 */
@Service
public class PersonHystrixService {

    @Autowired
    PersonService personService;


    //1 使用@HystrixCommand的fallbackMethod参数指定，当本方法调用失败时，调用后背方法fallbackMethod。
    @HystrixCommand(fallbackMethod = "fallbackSave")
    public Person save(){
        return personService.save();
    }

    public Person fallbackSave(){
        Person p = new Person("没有保存成功，Person Service 故障");
        return p;
    }
}
