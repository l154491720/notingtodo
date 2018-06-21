package com.notingtodo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by qilin.liu on 2018/6/20.
 */
@Entity //实体类注释
@Data //lombok 的注解，此注解后续可以自动生产 Getter、Setter 、toString 、 Constructor 、 Equals方法
@AllArgsConstructor //全部参数构造汉书
@NoArgsConstructor //无参数构造汉书
@EqualsAndHashCode(callSuper = false) //自动生成 equals方法、hashCode方法，并且不调用父类方法
public class SysUser extends AbstractAuditingEntity {

    private static final long serialVersionUID = 8272567265696620741L;

    @Id
    @GeneratedValue
    private Long id;

    @NotNull //校验不为空
    @Size(min = 1, max = 50) //长度校验
    @Column(length = 50, unique = true, nullable = false)
    private String username;

    @JsonIgnore
    @NotNull //校验不为空
    @Size(min = 60, max = 60) //长度校验
    @Column(length = 60)
    private String password;

    @Size(max = 50)
    @Column(length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(length = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    @Column(length = 100, unique = true)
    private String email;

    @Size(max = 256)
    @Column(name = "image_url",length = 256)
    private  String imageUrl;

    @JsonIgnore
    @ManyToMany(targetEntity = SysRole.class, fetch = FetchType.EAGER)
    @BatchSize(size = 20)
    private Set<SysRole> roles = new HashSet<>();

    @Transient //此注解 添加表中不存在的字段
    private Set<GrantedAuthority> authorities = new HashSet<>();

    public Set<GrantedAuthority> userAuthorities (){
        Set<GrantedAuthority> userAuthorities = new HashSet<>();
        for (SysRole role: this.roles){
            for (SysAutority autority : role.getAutorities()) {
                userAuthorities.add(new SimpleGrantedAuthority(autority.getValue()));
            }
        }
        return userAuthorities;
    }

}
