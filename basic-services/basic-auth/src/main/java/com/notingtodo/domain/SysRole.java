package com.notingtodo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by qilin.liu on 2018/6/20.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRole extends AbstractAuditingEntity {

    private static final long serialVersionUID = 168895510136775445L;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String value;

    /*
    fetch = FetchType.EAGER: 急加载，加载一个实体时，定义急加载的属性属性会立即从数据库中加载
    fetch = FetchType.LAZY: 懒加载，加载一个实体时，定义懒加载的属性不会立即从数据库中加载
    */
    @JsonIgnore
    @ManyToMany(targetEntity = SysAutority.class , fetch = FetchType.EAGER)
    @BatchSize(size=20) //批量操作大小
    private Set<SysAutority> autorities = new HashSet<>();

}
