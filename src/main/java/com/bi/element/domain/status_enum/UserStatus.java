package com.bi.element.domain.status_enum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum UserStatus {
    NORMAL(0, "正常"),
    DELETED(1, "已删除"),
    LOCKED(2, "已锁定"),
    ;

    @EnumValue
    private final Integer code;
    @JsonValue
    private final String message;

    UserStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
