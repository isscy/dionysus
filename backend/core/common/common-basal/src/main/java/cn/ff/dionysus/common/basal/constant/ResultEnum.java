package cn.ff.dionysus.common.basal.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * 返回结果（错误码）
 *
 * @author fengfan 2019/12/31
 */
@Getter
public enum ResultEnum {


    /**
     * 基础通用
     */
    SUCCESS(0, "成功"),
    ERROR(2, "失败"),
    FAIL(-1, "未知错误"),
    ALARM(-2, "警报"),

    /**
     * 服务器错误相关
     */
    SERVER_ERROR(5000, "系统错误"),
    SERVER_ERROR_501(5001, "微服务调用错误"),
    SERVICE_ERROR_UNAVAILABLE(5002, "微服务不可用"),
    GATEWAY_TIMEOUT(5004, "gateway超时"),

    /**
     * 账号及认证相关
     */
    CLIENT_ERROR(4000, "客户端请求错误"),
    CLIENT_ERROR_401(4001, "认证错误"),
    CLIENT_ERROR_403(4003, "无权访问"),
    CLIENT_PARAM_405(4005, "参数错误"),

    BAD_CREDENTIALS(4010, "bad_credentials"),   // ***
    ACCOUNT_DISABLED(4011, "账号已被禁用"),
    ACCOUNT_EXPIRED(4012, "账号已经过期"),
    CREDENTIALS_EXPIRED(4013, "token认证过期"),
    ACCOUNT_LOCKED(4014, "账号暂被锁定冻结"),
    USERNAME_NOT_FOUND(4015, "无效的用户名"),

    /**
     * 请求相关
     */
    BAD_REQUEST(4100, "bad_request: 错误的请求"),      // ***
    NOT_FOUND(4104, "not_found: 请求路径错误或资源已失效"), // ***
    METHOD_NOT_ALLOWED(4105, "请求方法错误"),             // ***
    MEDIA_TYPE_NOT_ACCEPTABLE(4106, "请求media_type错误"),
    TOO_MANY_REQUEST(4107, "请求次数超过限制"),
    TOO_QUICK_REQUEST(4108, "请求频率太快,请稍后再试"),

    /**
     * oauth2相关
     */
    INVALID_TOKEN(4200, "invalid_token: 无效的token"),   // ***
    INVALID_SCOPE(4201, "invalid_scope: 无效的范围参数"),
    INVALID_REQUEST(4202, "invalid_request"),
    INVALID_CLIENT(4203, "invalid_client: 请求来源非法"),
    INVALID_GRANT(4204, "invalid_grant"),
    REDIRECT_URI_MISMATCH(4205, "redirect_uri_mismatch"),
    UNAUTHORIZED_CLIENT(4206, "unauthorized_client"),          // ***
    EXPIRED_TOKEN(4207, "expired_token"),                 // ***
    UNSUPPORTED_GRANT_TYPE(4208, "unsupported_grant_type"),
    UNSUPPORTED_RESPONSE_TYPE(4209, "unsupported_response_type"),
    UNAUTHORIZED(4210, "unauthorized"),         // ***
    SIGNATURE_DENIED(4211, "signature_denied"),

    ACCESS_DENIED(4230, "[access_denied]无权限访问!"),   // ***
    ACCESS_DENIED_BLACK_IP_LIMITED(4231, "access_denied_black_ip_limited"),
    ACCESS_DENIED_WHITE_IP_LIMITED(4232, "[access_denied]黑名单IP无权访问"),
    ACCESS_DENIED_AUTHORITY_EXPIRED(4233, "[access_denied]授权已过期");


    private int code;
    private String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;

    }

    public static boolean isSuccess(int code) {
        return code == ResultEnum.SUCCESS.getCode();
    }


    public static String getMsgByCode(int code) {
        Optional<ResultEnum> opl = Arrays.stream(ResultEnum.values()).filter(e -> e.getCode() == code).findFirst();
        return opl.isPresent() ? opl.get().getMessage() : "";
    }

}

