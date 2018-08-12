package com.notingtodo.domain;

/**
 * Created by qilin.liu on 2018/6/19.
 */
public class Person {

    private String  id;

    private String name;

    private Integer age;

    private String address;

    public Person() {
        super();
    }

    public Person(String name) {
        super();
        this.name = name;
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
