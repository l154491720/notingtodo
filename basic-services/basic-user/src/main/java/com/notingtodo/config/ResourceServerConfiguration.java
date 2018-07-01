package com.notingtodo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 该类继承ResourceServerConfigurerAdapter类， 作为 Resource Server, 需要配置HttpSecurity 和
 * ResourceServerSecurityConfigurer 两个选项。HttpSecurity 配置了那些请求需要验证，那些请求不需要
 * 验证。".*swagger.*",".*v2.*",".*webjars.*","/user/login.*","/user/registry.*","/user/test.*" 不需要验证
 * 其他请求都需要验证
 * Created by qilin.liu on 2018/7/1.
 */
@Configuration
@EnableResourceServer //开启Resource Service的功能，并继承 ResourceServerConfigurerAdapter 进行配置
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    TokenStore tokenStore;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .regexMatchers(".*swagger.*",".*v2.*",".*webjars.*","/user/login.*","/user/registry.*","/user/test.*").permitAll()
                .antMatchers("/**").authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }
}