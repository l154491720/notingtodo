package com.notingtodo.config;


import com.notingtodo.security.UserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



/**
 * 由于需要对外暴露检查Token的API接口，所以basic-auth也是一个资源服务，需要在工程中引入Spring Security，
 * 并做相关配置，对auth-service资源进行保护
 * Created by qilin.liu on 2018/6/20.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserServiceDetail userServiceDetail;


    /**
     * HttpSecurity 中配置了所有请求要需要安全验证
     * @param http
     * @throws Exception
     */
    protected void configure(HttpSecurity http) throws Exception {
        //CSRF:因为不再依赖于Cookie，所以你就不需要考虑对CSRF（跨站请求伪造）的防范。
        http.
                authorizeRequests().anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

    /**
     * 密码编码器
     * @return
     */
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 重写Web安全配置适配器的授权管理配置方法
     * AuthenticationManagerBuilder中配置了验证信息源和密码加密的策略，并且向IOC容器中注入
     * AuthenticationManager对象。
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userServiceDetail)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    //不定义没有password grant_type，配置了验证管理的Bean
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean()throws Exception{
        return super.authenticationManagerBean();
    }

    /**
     * Spring Security 总结
     * 1. 首先需要引入 Spring Security 相关依赖
     * 2. 写一个配置类，该类需要继承 WebSecurityConfigurerAdapter , 并在该配置类上加 @EnableWebSecurity 注解开启
     *      Web Security。
     *      再需配置 AuthenticationManagerBuilder,  AuthenticationManagerBuilder 配置读取用户认证信息
     *      的方式，可以从内存中读取，也可以从数据库中读取，或者用其他方式。
     *      其次，需配置HttpSecurity, HttpSecurity 配置了请求的认证规则，即哪些 URI 请求需要认证、那些不要，以及
     *      需要什么权限才能访问。
     *      最后，如果需要开启方法级别上的安全配置，需要通过配置类上加 @EnableGlobalMethodSecurity 注解开启
     *      方法级别上的安全控制支持 secureEnabled、jsr250Enabled、和PostAuthorize 两种形式，一般只用到
     *      PreAuthorize 这种方式
     *
     */
}