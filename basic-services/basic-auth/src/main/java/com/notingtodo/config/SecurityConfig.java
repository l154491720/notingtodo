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
}