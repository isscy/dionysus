package cn.ff.dionysus.core.register.config;


import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * _
 *
 * @author fengfan 2020/3/23
 */
public class UnsafeUser {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        lock.lock();
    }



    private static Unsafe reflectGetUnsafe() throws NoSuchFieldException, IllegalAccessException {

        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        return (Unsafe) field.get(null);

    }
}
