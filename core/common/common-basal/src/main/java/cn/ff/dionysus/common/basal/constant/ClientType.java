package cn.ff.dionysus.common.basal.constant;

/**
 * 客户端
 *
 * @author fengfan 2019-07-26
 */
public enum ClientType {

    APP("app"),
    WEB("web"),

    IOS("ios"),
    ANDROID("android"),

    OUT("out"); // 外部第三方



    private String name;

    public String getName() {
        return name;
    }

    ClientType(String name) {
        this.name = name;
    }

    public static ClientType instance(String value) {
        for (ClientType statusEnum : values()) {
            if (statusEnum.getName().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }
    public static boolean isMove(String clientId){
        return IOS.getName().equals(clientId) || ANDROID.getName().equals(clientId);
    }
}
