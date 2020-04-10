package cn.ff.dionysus.core.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * webSocket
 *
 * @author fengfan 2020/4/10
 */
@SpringCloudApplication
@ConfigurationPropertiesScan
public class WsApp {


    public static void main(String[] args) {
        SpringApplication.run(WsApp.class, args);
    }
}
