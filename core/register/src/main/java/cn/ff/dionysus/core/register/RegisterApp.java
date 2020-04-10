package cn.ff.dionysus.core.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * _
 *
 * @author fengfan 2020/3/19
 */
@EnableScheduling
@SpringBootApplication
public class RegisterApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterApp.class);



    public static void main(String[] args) {
        /*System.setProperty(ConfigConstants.TOMCAT_DIR, "logs");
        System.setProperty(ConfigConstants.TOMCAT_ACCESS_LOG, "false");
        System.setProperty(ConfigConstants.STANDALONE_MODE, "true");*/
        LOGGER.info("args:{}", args);
        SpringApplication.run(RegisterApp.class, args);
    }
}
