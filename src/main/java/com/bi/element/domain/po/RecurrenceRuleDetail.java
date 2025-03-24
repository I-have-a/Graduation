package com.bi.element.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bi.element.domain.statusEnum.RuleType;
import lombok.Data;

import java.io.Serial;

@Data
@TableName("tb_recurrence_rule_detail")
public class RecurrenceRuleDetail implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    private Long ruleId;
    @TableField("type")
    private RuleType type;
    @TableField("value")
    private String value;
}
