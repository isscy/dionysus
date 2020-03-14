package cn.ff.dionysus.core.auth;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * _
 *
 * @author fengfan 2020/3/14
 */
public class PwdTest {

    @Test
    public void encode(){

        String str = "123456";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd = encoder.encode(str);
        System.out.println(pwd);
        System.out.println(encoder.matches(str, pwd));
    }
}
