package com.notingtodo.config;

import afu.org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * Created by qilin.liu on 2018/6/20.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 授权管理
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Redis连接工厂
     */
    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Autowired
    UserDetailsService userDetailsService;


    /**
     * token存在redis缓存数据库上
     */
    @Bean
    public RedisTokenStore tokenStore(){
        return new RedisTokenStore(connectionFactory); //初始化 RedisTokenStore 的 Bean
    }

    /**
     * 重写授权服务安全配置方法
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()") //所有访问都使用token
                .checkTokenAccess("isAuthenticated()"); //检验 token 的方法调研 isAuthenticated() 方法
    }

    /**
     * 重写授权服务端点配置方法
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)  //若无，refresh_token会有UserDetailsService is required错误
                .tokenStore(tokenStore());
    }

    /**
     * 重写客户端详情服务配置方法
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("android")
                .scopes("xx") //此处的scopes是无用的，可以随意设置
                .secret("android")
                .authorizedGrantTypes("password","authorization","refresh_token")
                .and()
                .withClient("webapp")
                .scopes("xx")
                .authorizedGrantTypes("implicit");
    }

}
