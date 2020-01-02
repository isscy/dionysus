package cn.ff.dionysus.common.basal.exception;

/**
 * 基础运行时异常
 *
 * @author fengfan 2019/12/31
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String EXT = "Base";

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getEXT(){
        return EXT;
    }

}
