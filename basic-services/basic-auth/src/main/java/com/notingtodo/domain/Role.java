package com.notingtodo.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * 实现 GrantedAuthority 接口，并重写 getAuthority 方法。权限点可以为任何字符串
 * 不一定是角色名的字符串，关键是 getAuthority 方法如何实现。
 * Created by qilin.liu on 2018/6/30.
 */
@Entity
public class Role implements GrantedAuthority{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}