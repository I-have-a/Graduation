package com.bi.element.domain.statusEnum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum RuleType {
    BY_WEEKLY("BY_WEEKLY", "每周"),
    BY_MONTH_DAY("BY_MONTH_DAY", "每月"),
    BY_YEAR_DAY("BY_YEAR_DAY", "每月"),
    ;
    @EnumValue
    private final String code;
    @JsonValue
    private final String message;

    RuleType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
