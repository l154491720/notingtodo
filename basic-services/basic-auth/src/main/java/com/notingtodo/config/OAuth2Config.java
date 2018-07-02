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
 * OAuth2 Provider 负责公开被 OAuth2 保护起来的资源。 OAuth2 Provider 需要配置代表用户的OAuth2 客户端信息，
 * 被用户允许的客户端就可以访问被 OAuth2 保护的资源。OAuth2 Provider 通过管理验证 OAuth2 令牌来控制客户端是否
 * 有权限访问其被保护的资源
 *
 * OAuth2 还必须提供认证 API 接口，根据认证 API接口，用户提供账号和密码等信息，来确认客户端是否可被 OAuth2 Provider
 * 授权。这样做的好处就是第三方客户端不需要获取用户的账号和密码，通过授权方式就可以访问被 OAuth2保护起来的资源了.
 *
 * OAuth2 Provider 的角色被分为 Authorization Service(授权服务) 和 Resource Service(资源服务)。 Spring OAuth2需要配合
 * Spring Security 一起使用，所有请求由 Spring MVC 控制器处理，并且经过一系列Spring Security过滤器。
 *
 * 在 Spring Security 过滤器链中有以下两个节点，这两个节点是向 Authorization Service 获取验证和授权的。
 * 1.授权点: /oauth/authorize.
 * 2.获取Token节点: 默认为/oauth/token.
 * Created by qilin.liu on 2018/6/30.
 */
@Configuration
@EnableAuthorizationServer //开启Authorization Server的功能，并且以Bean的形式注入ioc容器, 需要类继承AuthorizationServerConfigurerAdapter
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    /**
     * Authorization Server 配置
     * 在配置 Authorization Server 时，需要考虑客户端(Client)从用户获取访问令牌的授权类型(例如授权代码、用户凭据、刷新令牌)。
     * Authorization Server 需要配置客户端的详细信息和令牌服务的实现。
     *
     * 开启Authorization Server的功能，需要实现以下3个配置
     * 1.ClientDetailsServiceConfigurer 配置客户端信息
     * 2.AuthorizationServerEndpointsConfigurer 配置授权Token节点和Token服务
     * 3.AuthorizationServerSecurityConfigurer 配置 Token 节点的安全策略
     */
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

    /*
    AuthorizationServerSecurityConfigurer:
    如果资源服务和授权服务是在同一个服务中，用默认的配置即可，不需要做其他任何的配置。但是如果资源服务
    和授权服务不在同一个服务中，则需要做一些额外配置。如采用 RemoteTokenServices (远程Token校验)，资源
    服务器的每次请求所携带的 Token 都需要从授权服务做校验。 这时需要配置"/oauth/check_token" 校验节点
    的校验策略
     */
}