package com.notingtodo.controller;

import com.notingtodo.bean.Person;
import com.notingtodo.dao.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用rest风格
 * Created by qilin.liu on 2018/6/17.
 */

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @PostMapping
    public Person save(String name, String address,Integer age){
        Person p = personRepository.save(new Person(name,age,address));
        return p;
    }
}
