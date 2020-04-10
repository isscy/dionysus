package cn.ff.dionysus.core.auth.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * web、静态页、跨域 相关配置类
 * @author fengfan 2019-07-27
 */
@Configuration
@Slf4j
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebConfig {




    /**
     * 放过跨域
     *
     * @Bean public CorsFilter corsFilter() {
     * UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
     * CorsConfiguration corsConfiguration = new CorsConfiguration();
     * corsConfiguration.addAllowedOrigin("*");
     * corsConfiguration.addAllowedHeader("*");
     * corsConfiguration.addAllowedMethod("*");
     * corsConfiguration.setAllowCredentials(true);
     * source.registerCorsConfiguration("/**", corsConfiguration);
     * log.info("---------------------->>>>>>>>>>>>>>>>>>>>>>放开跨域拦截<<<<<<<<<<<<<<<<<<<<<<<---------------");
     * return new CorsFilter(source);
     * }
     */
    /*
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config); // CORS 配置对所有接口都有效
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        log.info("---------------------->>>>>>>>>>>>>>>>>>>>>>放开跨域拦截<<<<<<<<<<<<<<<<<<<<<<<---------------");
        bean.setOrder(0);
        return bean;
    }
    */
    @Bean
    public CharacterEncodingFilter filtercharacterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }




}
