package cn.ff.dionysus.common.security.exception;

import org.springframework.http.HttpStatus;

/**
 * _
 *
 * @author fengfan 2020/3/17
 */
public class UserAccountException extends BaseAuthenticationException {

    public UserAccountException(String msg) {
        super(msg);
    }

    @Override
    public int httpCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }

    @Override
    public String getEXT() {
        return "[账户错误]";
    }

}
