package cn.ff.dionysus.core.auth.config;

import cn.ff.dionysus.common.security.component.DefaultWebResponseExceptionTranslator;
import cn.ff.dionysus.common.security.utils.Oauth2Property;
import cn.ff.dionysus.core.auth.filter.LoginAuthenticationFilter;
import cn.ff.dionysus.core.auth.handler.DefaultAccessDeniedHandler;
import cn.ff.dionysus.core.auth.handler.DefaultAuthenticationEntryPoint;
import cn.ff.dionysus.core.auth.handler.LoginAuthenticationFailedHandler;
import cn.ff.dionysus.core.auth.handler.LoginAuthenticationSuccessHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 资源配置中心
 *
 * @author fengfan at 2019-07-26
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    //private final LoginAuthenticationFilter loginAuthenticationFilter;
    private final Oauth2Property oauth2Property;
    private final DefaultAccessDeniedHandler defaultAccessDeniedHandler;
    private final LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;
    private final LoginAuthenticationFailedHandler loginAuthenticationFailedHandler;
    private final AuthenticationManager authenticationManager;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        /*http
                .addFilterBefore(loginAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // Since we want the protected resources to be accessible in the UI as well we need
                // session creation to be allowed (it's disabled by default in 2.0.6)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers().anyRequest()
                .and()
                .anonymous()
                .and()
                .authorizeRequests()
                //配置/p访问控制，必须认证过后才可以访问
                .antMatchers("/test/**").permitAll()
                .anyRequest().authenticated();*/

        http.addFilterBefore(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests().antMatchers(oauth2Property.unCheckUrlArray()).permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .exceptionHandling().accessDeniedHandler(defaultAccessDeniedHandler)
                .authenticationEntryPoint(new DefaultAuthenticationEntryPoint());

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 定义异常转换类生效
        AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        ((OAuth2AuthenticationEntryPoint) authenticationEntryPoint).setExceptionTranslator(new DefaultWebResponseExceptionTranslator());
        resources.authenticationEntryPoint(authenticationEntryPoint);
    }


    @Bean
    public LoginAuthenticationFilter loginAuthenticationFilter() {
        LoginAuthenticationFilter filter = new LoginAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(loginAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(loginAuthenticationFailedHandler);
        return filter;
    }

}
