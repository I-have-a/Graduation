package com.bi.element.domain.statusEnum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum FrequencyEnum {

    DAILY("DAILY", "每天"),
    WEEKLY("WEEKLY", "每周"),
    MONTHLY("MONTHLY", "每月"),
    CUSTOM("CUSTOM", "自定义");

    @EnumValue
    private final String code;
    @JsonValue
    private final String message;

    FrequencyEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
