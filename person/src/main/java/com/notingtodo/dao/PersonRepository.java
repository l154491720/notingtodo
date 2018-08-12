package com.notingtodo.dao;


import com.notingtodo.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by qilin.liu on 2018/6/18.
 */
public interface PersonRepository extends JpaRepository<Person,String> {

    @Query("select p from Person p where p.id = ?1")
    Person getById(String id);

}
