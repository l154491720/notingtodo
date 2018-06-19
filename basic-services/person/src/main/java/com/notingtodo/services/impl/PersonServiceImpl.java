package com.notingtodo.services.impl;


import com.notingtodo.dao.PersonRepository;
import com.notingtodo.domain.Person;
import com.notingtodo.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by qilin.liu on 2018/6/18.
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person getById(String id) {
        return personRepository.getById(id);
//        return personRepository.findOne(id);
    }
}
