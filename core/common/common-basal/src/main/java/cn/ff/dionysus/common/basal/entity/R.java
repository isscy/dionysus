package cn.ff.dionysus.common.basal.entity;

import cn.ff.dionysus.common.basal.constant.ResultEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回结果封装
 *
 * @author fengfan 2019/12/31
 */
@Data
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    // 基本数据
    private int code;
    private String message;
    private T data;
    // 扩充属性

    private String EXT;


    private final static int SUCCESS_CODE = ResultEnum.SUCCESS.getCode();
    private final static int DEFAULT_ERROR_CODE = ResultEnum.ERROR.getCode();

    public R() {
    }

    public R(int code, String msg, T data, String ext) {
        this.code = code;
        this.message = msg;
        this.data = data;
        this.EXT = ext;
    }

    public static <T> R<T> ok() {
        return restResult(SUCCESS_CODE, null, null, null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(SUCCESS_CODE, null, data, null);
    }

    public static <T> R<T> ok(String message, T data) {
        return restResult(SUCCESS_CODE, message, data, null);
    }

    public static <T> R<T> fail(String msg) {
        return restResult(DEFAULT_ERROR_CODE, msg, null, null);
    }

    public static <T> R<T> fail(String msg, T data) {
        return restResult(DEFAULT_ERROR_CODE, msg, data, null);
    }

    public static <T> R<T> fail(Integer code, String msg, T data, String ext) {
        if (code == SUCCESS_CODE) {
            throw new RuntimeException("自定义错误编号失败！不能与SUCCESS_CODE相等");
        }
        return restResult(code, msg, data, ext);
    }

    public static <T> R<T> fail(ResultEnum resultEnum, String msg, T data, String ext) {
        if (resultEnum == ResultEnum.SUCCESS) {
            throw new RuntimeException("自定义错误编号失败！不能与SUCCESS_CODE相等");
        }
        return restResult(resultEnum.getCode(), msg, data, ext);
    }


    /*public String asJson() {
        return new Gson().toJson(this);
    }*/


    public boolean hadSuccess() {
        return this.code == SUCCESS_CODE;
    }

    private static <T> R<T> restResult(int code, String msg, T data, String ext) {
        return new R<>(code, msg, data, ext);
    }
}
