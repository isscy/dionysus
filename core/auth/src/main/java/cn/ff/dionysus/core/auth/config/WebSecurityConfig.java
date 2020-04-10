package cn.ff.dionysus.core.auth.config;

import cn.ff.dionysus.core.auth.config.provider.UserAuthenticationProvider;
import cn.ff.dionysus.core.auth.filter.LoginAuthenticationFilter;
import cn.ff.dionysus.core.auth.handler.LoginAuthenticationFailedHandler;
import cn.ff.dionysus.core.auth.handler.LoginAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * 权限配置
 *
 * @author fengfan 2020/2/19
 */
@Primary
@Order(90)
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserAuthenticationProvider userAuthenticationProvider;
    private final DaoAuthenticationProvider daoAuthenticationProvider;


    @Override
    @SneakyThrows
    protected void configure(HttpSecurity http) {
        /*http
                .formLogin()
                .loginPage("/token/login")
                .loginProcessingUrl("/token/form")
                .failureHandler(authenticationFailureHandler())
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/token/**",
                        "/actuator/**",
                        "/mobile/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();*/
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/error");
    }

    /**
     * 用户验证
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(userAuthenticationProvider)
                .authenticationProvider(daoAuthenticationProvider);
        /*.authenticationProvider(phoneAuthenticationProvider)*/
    }


    @Bean
    @Override
    @SneakyThrows
    public AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean();
    }

    /*@Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new FormAuthenticationFailureHandler();
    }*/


}
