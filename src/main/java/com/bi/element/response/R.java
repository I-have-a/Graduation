package com.bi.element.response;

import lombok.Data;

@Data
public class R {
    private String msg;
    private Object data;
    private Integer code;

    public R(String msg, Object data, Integer code) {
        this.msg = msg;
        this.data = data;
        this.code = code;
    }

    public static R ok(String msg, Object data, Integer code) {
        return new R(msg, data, code);
    }

    public static R ok(String msg, Object data) {
        return new R(msg, data, Code.SUCCESS);
    }

    public static R ok(Object data) {
        return new R("success", data, Code.SUCCESS);
    }

    public static R ok() {
        return new R("success", null, Code.SUCCESS);
    }

    public static R error(String msg, Object data, Integer code) {
        return new R(msg, data, code);
    }

    public static R error(Object data) {
        return new R("error", data, Code.FAIL);
    }

    public static R error() {
        return new R("error", null, Code.FAIL);
    }

    public static R error(String msg) {
        return new R(msg, null, Code.FAIL);
    }

    public static R error(Integer code, String msg) {
        return new R(msg, null, code);
    }

    public static R error(String msg, Object data) {
        return new R(msg, data, Code.FAIL);
    }
}
