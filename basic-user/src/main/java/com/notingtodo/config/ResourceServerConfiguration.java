package com.notingtodo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * Resource Server 资源服务器(可以是授权服务器,也可以是其他的资源服务器)
 * 提供了受 OAuth2 保护的资源，这些资源为 API 接口、 Html 页面、 Js 文件等
 * Spring OAuth2 提供了实现此保护功能的 Spring Security 认证过滤器。
 * 开启 Resource Server 的功能，需要配置一下的内容:
 *
 * 1.tokenService: 定义Token Service. 例如 ResourceServerTokenServices 类
 * 配置 Token 是如何解码的。 如果 Resource Server 和 Authorization Server
 * 是在同一个工程上，则不需要配置 tokenServices , 如果不在同一个程序就需要
 * 配置。也可以用 RemoteTokenServices类， 即 Resource Server 采用远程授权服务进行 Token
 * 解码，这时也不需要配置此选项。
 * 2.resourceId: 配置资源Id。
 *
 *
 * 该类继承ResourceServerConfigurerAdapter类， 作为 Resource Server, 需要配置HttpSecurity 和
 * ResourceServerSecurityConfigurer 两个选项。HttpSecurity 配置了那些请求需要验证，那些请求不需要
 * 验证。".*swagger.*",".*v2.*",".*webjars.*","/user/login.*","/user/registry.*","/user/test.*" 不需要验证
 * 其他请求都需要验证。
 *
 *
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