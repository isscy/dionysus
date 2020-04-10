package cn.ff.dionysus.core.auth.config;

import cn.ff.dionysus.common.basal.constant.SecurityConstant;
import cn.ff.dionysus.common.basal.entity.BaseUserDetail;
import cn.ff.dionysus.common.basal.entity.SysUser;
import cn.ff.dionysus.common.security.component.DefaultWebResponseExceptionTranslator;
import cn.ff.dionysus.common.security.component.SysTokenServices;
import cn.ff.dionysus.core.auth.service.DefaultClientDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证中心
 *
 * @author fengfan 2020/2/19
 */
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {


    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final SysTokenServices sysTokenServices;
    private AuthorizationServerEndpointsConfigurer endpoints;
    private final TokenStore tokenStore;
    private final TokenEnhancer tokenEnhancer;




    @Override
    @SneakyThrows
    public void configure(ClientDetailsServiceConfigurer clients) {
        clients.withClientDetails(/*new ClientDetailsRemoteImpl()*/new DefaultClientDetailsService());
    }






    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore)
                .tokenEnhancer(tokenEnhancer)
                .userDetailsService(userDetailsService)
                .reuseRefreshTokens(false)// refresh_token需要userDetailsService
                //.pathMapping("/oauth/confirm_access", "/token/confirm_access") //用来配置端点URL链接
                .exceptionTranslator(new DefaultWebResponseExceptionTranslator()); // 认证异常翻译
        this.endpoints = endpoints;
        //addUserDetailsService(sysTokenServices);
        endpoints.tokenServices(sysTokenServices);
    }





    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }
    
    
    

    /*@Bean
    @Primary
    @Lazy
    public AuthorizationServerTokenServices sysTokenServices() {
        SysTokenServices tokenServices = new SysTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);//支持刷新token
        tokenServices.setReuseRefreshToken(true);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        //addUserDetailsService(tokenServices);
        return tokenServices;
    }*/

    /*private void addUserDetailsService(SysTokenServices tokenServices) {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(userDetailsService));
        tokenServices.setAuthenticationManager(new ProviderManager(Collections.singletonList(provider)));
    }*/
}


/* ********************************************* ReadMe   ********************************************

 1. AuthorizationServerEndpointsConfigurer.pathMapping(默认url, 用户自定义的替换url), 第一个参数可为如下：
    /oauth/authorize：授权端点。
    /oauth/token：令牌端点。
    /oauth/confirm_access：用户确认授权提交端点。
    /oauth/error：授权服务错误信息端点。
    /oauth/check_token：用于资源服务访问的令牌解析端点。
    /oauth/token_key：提供公有密匙的端点，如果你使用JWT令牌的话。




 *****************************************************************************************************/
/* ********************************************* spring cloud oauth 认证流程   ********************************************

1.  AbstractAuthenticationProcessingFilter.doFilter
        判断当前filter是否可以处理当前请求(也就是是否包含用户名密码信息)，如果是，则调用其子类 UsernamePasswordAuthenticationFilter
        第一次请求时，没有用户名密码，是不会调用子类的
2.  BasicAuthenticationFilter





 */
