package com.notingtodo.services;


import com.notingtodo.domain.Person;

/**
 * Created by qilin.liu on 2018/6/18.
 */
public interface PersonService {

    Person save(Person person);

    Person getById(String id);
}
