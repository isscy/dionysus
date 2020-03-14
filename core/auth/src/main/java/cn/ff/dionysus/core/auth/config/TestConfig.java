package cn.ff.dionysus.core.auth.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

/**
 * 扫描或者发现配置类
 * 以下两种方法： 通过 @EnableConfigurationProperties 或者 @ConfigurationPropertiesScan
 * @author fengfan 2020/2/17
 */
@Configuration
@ConfigurationPropertiesScan(basePackages = {"cn.ff.dionysus.core.auth.config.properties"})

/*@Configuration
@EnableConfigurationProperties(value = {TestProperty.class} )*/
public class TestConfig {
}
