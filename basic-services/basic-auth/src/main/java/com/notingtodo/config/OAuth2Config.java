package com.notingtodo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * Created by qilin.liu on 2018/6/30.
 */
@Configuration
@EnableAuthorizationServer //开启Authorization Server的功能，并且以Bean的形式注入ioc容器, 需要类继承AuthorizationServerConfigurerAdapter
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
    /*
    ClientDetailsServiceConfigurer：
    配置客户端信息，客户端的配置信息可以放在内存中，也可以放在数据库中
    clientId: 客户端id，需要在 Authorization Server中是唯一的。
    secret: 客户端的密码
    scope: 客户端的域
    authorizedGrantTypes: 认证类型
    authorities: 权限信息
    客户端信息可以存储在数据库中，这样就可以通过更改数据库来实时更新客户端信息的数据。
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("uaa-service")
                .secret("123456")
                .scopes("service")
                .autoApprove(true)
                .authorizedGrantTypes("implicit","refresh_token", "password", "authorization_code")
                .accessTokenValiditySeconds(24*3600);//24小时过期
    }

    /*
   AuthorizationServerEndpointsConfigurer：
   在默认情况下, AuthorizationServerEndpointsConfigurer 配置开启了所有的验证类型，除了密码类型的验证，
   密码类型的验证只有配置了 authenticationManager 的配置才会开启，
   authenticationManager: 只有配置了该选项密码认证才会开启，在大多数情况下都是密码认证，所以一般会配置这个选项
   userDetailsService: 配置获取用户认证信息的接口
   authorizationCodeServices: 配置验证码服务
   implicitGrantService: 管理配置 implict 验证的状态
   tokenGranter: 配置 Token Granter

   另外，需要设置Token的管理策略，目前支持一下3种
   inMemoryTokenStore: Token存储在内存中
   JdbcTokenStore: Token存储在数据库中，需要引入spring-jdbc的依赖包，并配置了数据源，以及初始化 spring OAuth2的数据库脚本
   JwtTokenStore: 采用JWT形式，这种形式没有做任何的存储,因为JWT本身包含了用户验证的所有信息，不需要存储。采用这种新式需要引入spring-jwt的依赖
    */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtTokenEnhancer()).authenticationManager(authenticationManager);
    }

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }


    /*
    在AuthorizationServerEndpointsConfigurer 的配置中，配置JwtTokenStore 时需要使用jks文件作为Token 加密
    的秘钥。jks 文件是使用 Java keytool 生成的，在生成 jks 文件之前需要保证 JDK 已经安装。打开计算机终端
    输入命令:
    keytool -genkeypair -alias notingtodo-jwt -validity 3650 -keyalg RSA -dname "CN=jwt, OU=jtw,
    O=jtw,L=zurich,S=zurich,C=CH" -keypass notingtodo -keystore notingtodo-jwt.jks -storepass notingtodo

    alias选项为别名，-keypass和-storepass为密码选项，validity为配置jks文件的过期天数

    获取jks 文件作为私钥，只允许Uaa服务持有，并用作加密JWT。需要解密的服务需要使用 jks 文件的公钥
    获取 jks 文件的公钥命令如下:
    keytool -list -rfc --keystore notingtodo-jwt.jks | openssl x509 -inform pem -pubkey

    在计算机终端输入上面的命令，提示需要密码, notingtodo 输入即可显示公钥信息

     */
    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("notingtodo-jwt.jks"), "notingtodo".toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("notingtodo-jwt"));
        return converter;
    }
}