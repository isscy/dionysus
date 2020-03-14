package cn.ff.dionysus.common.security.exception;

import cn.ff.dionysus.common.security.component.AuthExceptionSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * _
 *
 * @author fengfan 2020/2/25
 */
@JsonSerialize(using = AuthExceptionSerializer.class)
public class UnauthorizedException extends DefaultAuthException {


    public UnauthorizedException(String msg) {
        super(msg);
    }
    public UnauthorizedException(String msg, Throwable t) {
        super(msg, t);
    }

    public UnauthorizedException(String msg, Integer errorCode, String ext) {
        super(msg, errorCode, ext);

    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }


}
