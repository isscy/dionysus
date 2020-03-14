package cn.ff.dionysus.core.auth.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;

/**
 * 基于构造器的参数绑定
 * springBoot 2.2 以后使用
 * @ConstructorBinding 不能和 @Component 一起使用
 * @author fengfan 2020/2/17
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "wanner")
public class TestProperty {


    private String to;
    private String from;
    private Date time;
    private InetAddress ip;
    private UserInfo detail;

    public TestProperty(String to, @DefaultValue("China") String from,
                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date time, InetAddress ip, UserInfo detail) {
        this.to = to;
        this.from = from;
        this.time = time;
        this.ip = ip;
        this.detail = detail;
    }

    public static class UserInfo {
        private String tel;
        private List<String> roles;

        public UserInfo(String tel, @DefaultValue("USER") List<String> roles) {
            this.tel = tel;
            this.roles = roles;
        }

    }

    @Override
    public String toString() {
        return "TestProperty{" +
                "to='" + to + '\'' +
                ", from='" + from + '\'' +
                ", time=" + time +
                ", ip=" + ip +
                ", detail=" + detail +
                '}';
    }
}
