package cn.ff.dionysus.core.register.config;

/**
 * 单例模式
 *
 * @author fengfan 2020/3/21
 */
public class Singleton {
    public static void main(String[] args) {

        LazySingleton lazySingleton = LazySingleton.getInstance();
        HungrySingleton hungrySingleton = HungrySingleton.getInstance();
        InnerClassSingleton innerClassSingleton = InnerClassSingleton.getInstance();

    }
}

//懒汉模式 - double check
class LazySingleton {
    private volatile static LazySingleton instance; //放置指令重排，导致使用尚未初始化的实例

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null)
                    instance = new LazySingleton();
            }
        }
        return instance;
    }
}
// 饿汉模式
class HungrySingleton{
    private static HungrySingleton instance = new HungrySingleton();
    private HungrySingleton(){}
    public static HungrySingleton getInstance(){
        return  instance;
    }
}
// 静态内部类
class  InnerClassSingleton{
    private static class InnerClassHolder{
        private static InnerClassSingleton instance = new InnerClassSingleton();
    }
    private InnerClassSingleton(){}
    public static InnerClassSingleton getInstance(){
        return InnerClassHolder.instance;
    }
}
