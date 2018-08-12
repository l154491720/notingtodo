package com.notingtodo.web;


import com.notingtodo.domain.Person;
import com.notingtodo.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by qilin.liu on 2018/6/18.
 */
@RestController
@RequestMapping("/person")
public class PersonController {



    @Autowired
    PersonService personService;

    @PostMapping
    public Person save(){
        UUID uuid = UUID.randomUUID();
        return personService.save(new Person(uuid.toString(),"aaa",13,"厦门"));
    }

    @GetMapping
    public Person getById(@Param("id") String id){
        return personService.getById(id);
    }

}
