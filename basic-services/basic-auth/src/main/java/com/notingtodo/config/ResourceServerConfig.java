package com.notingtodo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;

/**
 * basic-auth提供user信息，所以basic-auth也是一个Resource Server
 * Created by qilin.liu on 2018/6/20.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request,response,authException)->
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED)) //如果捕获一次返回 返回状态码401
                .and()
                    .authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                    .httpBasic();
    }
}
