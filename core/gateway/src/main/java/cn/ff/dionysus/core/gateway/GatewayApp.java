package cn.ff.dionysus.core.gateway;

/**
 * _
 *
 * @author fengfan 2020/1/3
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 认证中心 授权管理
 *
 * @author fengfan 2019-06-03
 */
@SpringCloudApplication
@ConfigurationPropertiesScan
public class GatewayApp {


    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class, args);
    }
}
