package cn.ff.dionysus.core.register.config.proxy;

import java.time.LocalDateTime;

/**
 * _
 *
 * @author fengfan 2020/4/9
 */
public class StaticProxy implements Subject {
    private RealSubject realSubject;
    private Integer number = 0;

    public StaticProxy(int number) {
        this.number = number;
    }

    @Override
    public int mixed(int a, long b, float c, double d) {
        System.out.println("proxyToMix");
        return number * (int) (a + b * c / d);
    }

    @Override
    public String insert(int count, String prefix, String suffix) {
        return prefix + count + suffix + 'a';
    }

    @Override
    public void display(LocalDateTime dateTime) {
        System.out.println();
    }
}
