package com.bi.element.domain.statusEnum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ItemComplexity {
    //(0=简单,1=中等,2=困难)
    SIMPLE(0, "简单"),
    MEDIUM(1, "中等"),
    DIFFICULTY(2, "困难"),
    ;

    @EnumValue
    private final Integer code;
    @JsonValue
    private final String message;

    ItemComplexity(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
