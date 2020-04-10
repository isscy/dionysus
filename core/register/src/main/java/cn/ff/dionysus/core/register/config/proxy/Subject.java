package cn.ff.dionysus.core.register.config.proxy;

import java.time.LocalDateTime;

/**
 * 抽象接口
 *
 * @author fengfan 2020/4/9
 */
public interface Subject {

    int mixed(int a, long b, float c, double d);

    String insert(int count, String prefix, String suffix);

    void display(LocalDateTime dateTime);
}
