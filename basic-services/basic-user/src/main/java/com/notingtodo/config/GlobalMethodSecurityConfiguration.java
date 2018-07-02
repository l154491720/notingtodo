package com.notingtodo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * 配置全局方法安全
 * Spring Security 方法级别上的保护
 * Spring Security 从2.0版本开始，提供了方法级别的安全支持，并提供 JSR-250 的支持。
 * @EnableGlobalMethodSecurity 注解开启了方法级别的保护，括号后面的参数可选，可选参数如下。
 * 1.prePostEnabled: Spring Security 的 pre 和 post 注解是否可用，即 @PreAuthorize 和 @PostAuthorize 是否可用
 * 2.secureEnabled: Spring Security 的 @Secured 注解是否可用
 * 3.jsr250Enabled: Spring Security 的 JSR-250 的注解是否可用。
 *
 * @PreAuthorize 注解会在进入方法前进行权限验证
 * @PostAuthorize 注解会在方法执行后再进行权限验证，这个注解的使用场景很少
 *
 * @PreAuthorize("hasRole('ADMIN')") 也可以写成 @PreAuthorize("hasAuthority('ROLE_ADMIN')") 二者等价
 * 若有二个权限点
 * @PreAuthorize("hasAnyRole('ADMIN','USER')") 也可以写成 @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
 *
 * Created by qilin.liu on 2018/7/1.
 */
/*
Spring Security 默认是禁用注解的，要想开启注解，需要在继承WebSecurityConfigurerAdapter的类上加@EnableGlobalMethodSecurity注解
，来判断用户对某个控制层的方法是否具有访问权限
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GlobalMethodSecurityConfiguration {
}