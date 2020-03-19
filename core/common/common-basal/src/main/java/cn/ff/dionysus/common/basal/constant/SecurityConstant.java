package cn.ff.dionysus.common.basal.constant;

/**
 * 权限相关的常量类和全局方法
 *
 * @author fengfan 2020/2/19
 */
public class SecurityConstant {

    /**
     * 角色前缀
     */
    public final static String ROLE = "ROLE_";

    /**
     * oauth2 缓存统一前缀
     */
    public final static String PROJECT_OAUTH_ACCESS = "dionysus";


    /**
     * 登陆方式
     */
    public final static String LOGIN_TYPE_PHONE = "phone";

    /**
     * 超级管理员的ID
     */
    public final static int SUPER_ADMIN_ID = 1;


    /**
     * grant_type_list
     */
    public final static String REQUEST_GRANT_TYPE_QR = "qr";  // APP 二维码
    public final static String REQUEST_GRANT_TYPE_USER = "user"; // username/password
    public final static String REQUEST_GRANT_TYPE_PHONE = "phone"; // phone/code
    public final static String REQUEST_GRANT_TYPE_WXM = "wx-mini"; // weChat mini code
}
