package cn.ff.dionysus.core.auth;

/**
 * _
 *
 * @author fengfan 2020/1/3
 */

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 认证中心 授权管理
 *
 * @author fengfan 2019-06-03
 */
@SpringCloudApplication
//@MapperScan("cn.redsoft.cloudplatform.auth.mapper")
public class AuthApp {


    public static void main(String[] args) {
        SpringApplication.run(AuthApp.class, args);
    }
}
