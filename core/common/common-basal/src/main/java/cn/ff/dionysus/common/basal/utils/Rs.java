package cn.ff.dionysus.common.basal.utils;

import cn.ff.dionysus.common.basal.constant.ResultEnum;
import cn.ff.dionysus.common.basal.entity.R;

/**
 * 返回结果封装
 *
 * @author fengfan 2020/2/24
 */
public class Rs {

    public static R ok() {
        return new R<>().ok();
    }

    public static <T> R<T> ok(T data) {
        return new R<T>().ok(data);
    }

    public static R fail(String msg) {
        return new R().fail(msg);
    }

    public static R fail(int code, String msg) {
        return new R().fail(code, msg, null, null);
    }

    public static <T> R<T> fail(int code, String msg, T data) {
        return new R<T>().fail(code, msg, data, null);
    }

    public static R fail(ResultEnum entity) {
        return new R().fail(entity.getCode(), entity.getMessage(), null, null);
    }

    public static <T> R<T> fail(int code, String msg, T data, String ext) {
        return new R<T>().fail(code, msg, data, ext);
    }
}
