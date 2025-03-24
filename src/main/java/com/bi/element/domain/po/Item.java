package com.bi.element.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.bi.element.domain.statusEnum.ItemComplexity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("tb_item")
public class Item extends BaseBean implements java.io.Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    Long id;

    /**
     * 事件名
     */
    @NotBlank(message = "事件名不能为空")
    private String title;

    /**
     * 封面地址
     */
    @TableField("cover_address")
    private String coverAddress;

    /**
     * 是否重复
     */
    @TableField("is_recurring")
    private Boolean isRecurring;

    /**
     * 重复类型
     */
    @TableField(exist = false)
    private RecurrenceRule recurrenceType;

    @TableField("recurrence_rule_id")
    private Long recurrenceRuleId;

    /**
     * 父事件id
     */
    @TableField("parent_id")
    private Long parentId;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate eventDate;

    private Integer year;
    private Integer month;
    private Integer day;

    /**
     * 深度
     */
    private Integer depth;

    /**
     * 路径
     */
    private String path;

    /**
     * 创建者Id
     */
    @TableField("owner_id")
    private Long ownerId;

    /**
     * 描述
     */
    @TableField("item_describe")
    private String itemDescribe;

    /**
     * 删除标志位
     */
    @TableField("del_flag")
    private Boolean delFlag;

    /**
     * 难度
     */
    @TableField("complexity")
    private ItemComplexity complexity;

    @Version
    private Integer version;

    /**
     * 子事件
     */
    @TableField(exist = false)
    private List<Item> subItems;

    /**
     * 用户
     */
    @TableField(exist = false)
    private List<User> users;
}
