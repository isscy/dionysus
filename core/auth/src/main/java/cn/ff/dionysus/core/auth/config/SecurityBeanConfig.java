package cn.ff.dionysus.core.auth.config;

import cn.ff.dionysus.common.basal.constant.SecurityConstant;
import cn.ff.dionysus.common.basal.entity.BaseUserDetail;
import cn.ff.dionysus.common.basal.entity.SysUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.HashMap;
import java.util.Map;

/**
 * 权限相关的配置类
 *
 * @author fengfan 2020/4/10
 */
@Configuration
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityBeanConfig {


    private final RedisConnectionFactory redisConnectionFactory;
    private final UserDetailsService userDetailsService;

    /**
     * 自定义加密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public TokenStore tokenStore() {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix(SecurityConstant.PROJECT_OAUTH_ACCESS);
        return tokenStore;
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> additionalInfo = new HashMap<>(8);
            SysUser sysUser = ((BaseUserDetail) authentication.getPrincipal()).getSysUser();
            additionalInfo.put("userId", sysUser.getId());
            additionalInfo.put("nickName", sysUser.getNickName());
            additionalInfo.put("userName", sysUser.getUserName());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

}
