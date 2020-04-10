package cn.ff.dionysus.core.register.constant;

/**
 * _
 *
 * @author fengfan 2020/3/20
 */
public class NacosConstant {
    /**
     *  配置key
     */
    public static final String MODE_PROPERTY_KEY_STAND_MODE = "nacos.mode";
    public static final String MODE_PROPERTY_KEY_FUNCTION_MODE = "nacos.function.mode";
    public static final String LOCAL_IP_PROPERTY_KEY = "nacos.local.ip";

    /**
     * mode
     */
    public static final String STANDALONE_MODE_ALONE = "standalone";
    public static final String STANDALONE_MODE_CLUSTER = "cluster";
    /**
     * 当前mode : 是否为单机
     */
    public static final boolean STANDALONE_MODE = Boolean.getBoolean("nacos.standalone");
    public static String LOCAL_IP;
}
