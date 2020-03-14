package cn.ff.dionysus.core.auth;

/**
 * _
 *
 * @author fengfan 2020/1/3
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 认证中心 授权管理
 *
 * @author fengfan 2019-06-03
 */
@SpringCloudApplication
/*@SpringBootApplication
@EnableDiscoveryClient*/
//@MapperScan("cn.redsoft.cloudplatform.auth.mapper")
@EnableFeignClients(basePackages = "cn.ff.dionysus")
@MapperScan(basePackages = {"cn.ff.dionysus"})
public class AuthApp {


    public static void main(String[] args) {
        SpringApplication.run(AuthApp.class, args);
    }
}
