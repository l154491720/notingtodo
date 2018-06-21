package com.notingtodo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

/**
 * 实体的基本抽象类，创建时会被定义
 * Created by qilin.liu on 2018/6/20.
 */

/**
 * 说明:
 * @MappedSuperclass:
 *      ① @MappedSuperclass注解使用在父类上面，用来标识父类的
 *      ② @MappedSuperclass标识的类表示其不能映射到数据库表，因为其不是一个完整的实体类
 *          但是他所拥有的属性能够映射在其子类对应的数据库表中
 *      ③ @MappedSuperclass标识的类不能再有@Entity或@Table注解
 *
 * @Audited:
 *      此注解审计该实体(网上解释比较少，也不是很清楚)
 *
 * @EntityListeners:
 *      监听该实体生命周期的注解，当被监听的实体或子类上发生生命周期事件,JPA 持续性提供按
 *      监听程序定义的顺序通知每个实体监听程序，并调用相应的生命周期事件。
 *
 * @Data:
 *      注解在类上，为类提供读写属性，此外还提供了equals()、hashCode()、toString()方法
 *      其实就是加了这个属性就不用写什么 getter 和 setter 方法了
 */
@MappedSuperclass
@Audited
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class AbstractAuditingEntity implements Serializable{


    private static final long serialVersionUID = -2281274616741220888L;



    @CreatedBy //自动设置创建人，下同
    @Column(name = "create_by", nullable = false, length = 50, updatable = false)
    @JsonIgnore //此注解在 json 序列化 java bean 时忽略此属性，序列化和反序列化都受影响
    private String createBy;

    @CreatedDate //自动设置创建时间，下同
    @Column(name = "create_data", nullable = false)
    @JsonIgnore
    private Instant createdDate = Instant.now();

    @LastModifiedBy
    @Column(name = "last_modified_by", length = 50)
    @JsonIgnore
    private String lastModifyBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonIgnore
    private Instant lastModifiedDate = Instant.now();
}

