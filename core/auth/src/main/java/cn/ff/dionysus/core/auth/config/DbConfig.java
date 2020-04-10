package cn.ff.dionysus.core.auth.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mysql、mybatis、 druid 相关的配置类
 *
 * @author fengfan 2020/4/10
 */
@Configuration
@Slf4j
public class DbConfig {


    /**
     * 分页
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // paginationInterceptor.setLimit(你的最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }
    /**
     * 打印 sql

     @Bean public PerformanceInterceptor performanceInterceptor() {
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
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }


    /**
     * druid 监控配置
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //servletRegistrationBean.addInitParameter("allow","192.168.1.12,127.0.0.1");//IP白名单
        //servletRegistrationBean.addInitParameter("deny","192.168.4.23");//IP黑名单
        servletRegistrationBean.addInitParameter("loginUsername", "admin"); //控制台用户
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        servletRegistrationBean.addInitParameter("resetEnable", "false");//是否能够重置数据
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean statFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");//添加过滤规则
        return filterRegistrationBean;
    }

}
