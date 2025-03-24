package com.bi.element.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bi.element.domain.statusEnum.FrequencyEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@TableName("tb_recurrence_rule")
public class RecurrenceRule {
    List<RecurrenceRuleDetail> details;
    // 主键
    private Long id;
    // 频率
    private FrequencyEnum frequency;
    // 间隔
    private Integer interval;
    // 开始时间
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    // 结束时间
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    // 自定义日期列表，JSON格式，如：["2023-01-01","2023-01-02"]
    private String customDates;
}
