package cn.ff.dionysus.core.auth.config;

import cn.ff.dionysus.common.basal.constant.SecurityConstant;
import cn.ff.dionysus.common.basal.entity.BaseUserDetail;
import cn.ff.dionysus.common.basal.entity.SysUser;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.HashMap;
import java.util.Map;

/**
 * _
 *
 * @author fengfan 2019-07-27
 */
@Configuration
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApiBeanConfig {

    private final RedisConnectionFactory redisConnectionFactory;

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


    /**
     * 放过跨域

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", corsConfiguration);
        log.info("---------------------->>>>>>>>>>>>>>>>>>>>>>放开跨域拦截<<<<<<<<<<<<<<<<<<<<<<<---------------");
        return new CorsFilter(source);
    }
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
        CharacterEncodingFilter characterEncodingFilter =new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }


    /**
     * 分页
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // paginationInterceptor.setLimit(你的最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制);
        return paginationInterceptor;
    }
    /**
     * 打印 sql

    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        Properties properties = new Properties();//格式化sql语句
        properties.setProperty("format", "true");
        performanceInterceptor.setProperties(properties);
        return performanceInterceptor;
    }
     */
    /**
     * 乐观锁
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor(){
        return new OptimisticLockerInterceptor();
    }


    /**
     * druid 监控配置
     */
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean servletRegistrationBean=new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        //servletRegistrationBean.addInitParameter("allow","192.168.1.12,127.0.0.1");//IP白名单
        //servletRegistrationBean.addInitParameter("deny","192.168.4.23");//IP黑名单
        servletRegistrationBean.addInitParameter("loginUsername","admin"); //控制台用户
        servletRegistrationBean.addInitParameter("loginPassword","123456");
        servletRegistrationBean.addInitParameter("resetEnable","false");//是否能够重置数据
        return servletRegistrationBean;
    }
    @Bean
    public FilterRegistrationBean statFilter(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");//添加过滤规则
        return filterRegistrationBean;
    }




}
