package cn.ff.dionysus.common.security.exception;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import cn.ff.dionysus.common.security.component.AuthExceptionSerializer;
import lombok.Getter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * _
 *
 * @author fengfan 2020/2/25
 */
@JsonSerialize(using = AuthExceptionSerializer.class)
public class DefaultAuthException extends OAuth2Exception {

    @Getter
    private Integer errorCode;

    private String EXT = "[认证异常]";

    public DefaultAuthException(String msg) {
        super(msg);
    }

    public DefaultAuthException(String msg, Throwable t) {
        super(msg, t);
    }

    public DefaultAuthException(String msg, Integer errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }


    public DefaultAuthException(String msg, Integer errorCode, String ext) {
        this(msg, errorCode);
        this.EXT = ext;
    }

    public String getEXT() {
        return EXT;
    }
}
