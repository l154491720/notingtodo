package com.notingtodo.dao;

import com.notingtodo.bean.Person;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

/**
 * Created by qilin.liu on 2018/6/17.
 */
public interface PersonRepository extends JpaRepository<Person,String> {

    List<Person> findByAddress(String name);

    Person findByNameAndAddress(String name, String address);

    @Query("select p from Person p where p.name = :name and p.address=:address")
    Person withNameAndAddressQuery(@Param("name") String name,@Param("address") String address);

    List<Person> withNameAndAddressNameQuery(String name, String address);
}
