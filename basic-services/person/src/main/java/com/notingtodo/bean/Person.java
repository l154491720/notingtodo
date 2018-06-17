package com.notingtodo.bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 * Created by qilin.liu on 2018/6/17.
 */
@Entity
@NamedQuery(name="Person.withNameAndAddressNameQuery", query = "select p from Person p where p.name =?1 and p.address=?2")
public class Person {

    @Id
    private String id;

    private String name;

    private Integer age;

    private String address;

    public Person( String name, Integer age, String address) {

        this.name = name;
        this.age = age;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
