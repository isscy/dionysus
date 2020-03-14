package cn.ff.dionysus.common.security.exception;

import cn.ff.dionysus.common.security.component.AuthExceptionSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;

/**
 * 系统内部异常
 *
 * @author fengfan 2020/3/11
 */
@JsonSerialize(using = AuthExceptionSerializer.class)
public class ServerErrorException extends DefaultAuthException  {


    public ServerErrorException(String msg) {
        super(msg);
    }

    public ServerErrorException(String msg, Throwable t) {
        super(msg, t);
    }

    public ServerErrorException(String msg, Integer errorCode) {
        super(msg, errorCode);
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
    @Override
    public String getEXT() {
        return "[系统内部错误]";
    }


}
