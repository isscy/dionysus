package cn.ff.dionysus.core.register.config.proxy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * _
 *
 * @author fengfan 2020/4/9
 */
public class RealSubject implements Subject {
    @Override
    public int mixed(int a, long b, float c, double d) {
        return (int)(a + b * c / d) ;
    }

    @Override
    public String insert(int count, String prefix, String suffix) {
        return prefix + count + suffix;
    }

    @Override
    public void display(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMMM-dd HH:mm", Locale.CHINA);
        System.out.println(dateTime.format(formatter));
    }
}
