package com.example.graduate.response;

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
}
