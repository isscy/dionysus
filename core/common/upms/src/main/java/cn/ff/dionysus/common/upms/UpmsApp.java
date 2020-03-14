package cn.ff.dionysus.common.upms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * _
 *
 * @author fengfan 2020/2/20
 */
@EnableResourceServer
@EnableFeignClients
@SpringCloudApplication
@MapperScan(basePackages = {"cn.ff.dionysus"/*, "cn.hicloud.zwangce"*/})
public class UpmsApp {

    //todo 看一下 EnablePigFeignClients 自定义feignClient
    //todo 看一下 @EnablePigFeignClients 自定义resourceServer

    public static void main(String[] args) {
        SpringApplication.run(UpmsApp.class, args);
    }
}
