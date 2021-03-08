package cn.ff.dionysus.common.core.constant;

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
    SUCCESS(1, "成功", "success"),
    ERROR(2, "失败", "failure"),
    FAIL(-1, "未知错误", "unknown"),
    ALARM(-2, "警告", "warning"),

    /**
     * 服务器错误相关 50开头的四位数字
     */
    SERVER_ERROR(5000, 500, "系统错误", "Server Error"),
    SERVER_ERROR_501(5001, "微服务调用错误", "Micro-service call error"),
    SERVICE_ERROR_UNAVAILABLE(5003, "微服务不可用", "Micro-service Unavailable"),
    GATEWAY_TIMEOUT(5004, "网关超时", "Gateway Time-out"),

    /**
     * 客户端异常 40开头的四位数字
     */
    CLIENT_ERROR(4000, "客户端请求错误"),
    CLIENT_AUTH_401(4001, 401, "认证错误", "User authentication failed"),
    CLIENT_ACCESS_403(4003, 403, "无权访问", "User does not have access"),
    CLIENT_ERROR_404(4004, 404, "找不到请求的资源", "Not found"),
    CLIENT_METHOD_405(4005, 405, "请求方法错误", "Request method error"),
    CLIENT_PARAM_406(4006, 400, "参数错误", "Request parameter error"),
    CLIENT_QUICK_407(4007, 400, "客户端请求过快,", "Client request is too fast"),
    // 权限check不通过 4010 --4030
    BAD_CREDENTIALS(4010, 401, "鉴权失败", "Bad credentials"),   // 不好归类的鉴权异常 统一返回这个
    CREDENTIALS_MISSING(4011, 401, "token缺失", "Missing credentials token "),   // 需要check权限的请求 但没有带token
    CREDENTIALS_EXPIRED(4012, 401, "token认证过期", "User token authentication expired"), // token 过期失效
    CREDENTIALS_PARSE_ERROR(4013, 401, "token错误，无法解析", "User token parsing error"), // token 解析失败

    ACCOUNT_UNABLE(4020, 401, "账号不存在或已被禁用", "Account does not exist or has been disabled"),
    ACCOUNT_LOCKED(4021, 401, "账号被锁定冻结或者禁用", "Account is temporarily locked and frozen"),
    ACCOUNT_EXPIRED(4022, 401, "临时账号已经过期", "Temporary account has expired"),
    USER_VERIFY_CODE_ERR(4030, 401, "验证码错误", "Verification code error"),

    /**
     * 业务异常--------------------------- 统一业务异常 1XXXX
     */
    INCOMPLETE_INCOMING_DATA(10001,"传入数据不完整","Incomplete incoming data"),
    INCOMING_PARAMETER_ERROR(10002,"传入数据错误","Incoming parameter error"),
    UNABLE_GET_USER_INFO(10011,"无法获取用户信息","Unable to get user information"),

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


    // 状态
    private int code;
    private String message;
    // http状态码
    private int httpCode;
    private String englishMessage;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    ResultEnum(int code, int httpCode, String message, String englishMessage) {
        this.code = code;
        this.httpCode = httpCode;
        this.message = message;
        this.englishMessage = englishMessage;
    }

    ResultEnum(int code, String message, String englishMessage) {
        this.code = code;
        this.httpCode = 200;
        this.message = message;
        this.englishMessage = englishMessage;
    }


    public static boolean isSuccess(int code) {
        return code == ResultEnum.SUCCESS.getCode();
    }


    public static String getMsgByCode(int code) {
        Optional<ResultEnum> opl = Arrays.stream(ResultEnum.values()).filter(e -> e.getCode() == code).findFirst();
        return opl.isPresent() ? opl.get().getMessage() : "";
    }

    public static String getEnMsgByCode(int code) {
        Optional<ResultEnum> opl = Arrays.stream(ResultEnum.values()).filter(e -> e.getCode() == code).findFirst();
        return opl.isPresent() ? opl.get().getEnglishMessage() : "";
    }

}


