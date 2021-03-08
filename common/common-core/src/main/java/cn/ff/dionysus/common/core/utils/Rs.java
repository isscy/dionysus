package cn.ff.dionysus.common.core.utils;


import cn.ff.dionysus.common.core.constant.ResultEnum;
import cn.ff.dionysus.common.core.entity.R;

/**
 * 返回结果封装
 *
 * @author fengfan 2020/2/24
 */
public class Rs {

    public static final Integer successCode = ResultEnum.SUCCESS.getCode();

    public static R ok() {

        return new R(successCode);
    }

    public static <T> R<T> ok(T data) {
        return new R<T>(successCode, null, data);
    }

    public static <T> R<T> ok(String message, T data) {
        return new R<>(successCode, message, data);
    }

    public static <T> R<T> ok(Integer code, String message, T data) {
        return new R<>(code, message, data);
    }

    public static R fail(String msg) {
        return new R<>(ResultEnum.FAIL.getCode(), msg, null);
    }

    public static R fail(ResultEnum resultEnum) {
        R r = new R<>(resultEnum.getCode(), resultEnum.getMessage(), null);
        r.setHttpCode(resultEnum.getHttpCode());
        return r;
    }

    public static R fail(int code, String msg) {
        return new R<>(code, msg, null);
    }

    public static <T> R<T> fail(int code, String msg, T data) {
        return new R<>(code, msg, data);
    }


}
