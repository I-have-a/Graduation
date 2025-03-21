package com.bi.element.domain.vo;

import com.bi.element.domain.po.BaseBean;
import com.bi.element.domain.po.RecurrenceRule;
import com.bi.element.domain.po.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ItemVO extends BaseBean implements java.io.Serializable {

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
    private String coverAddress;

    /**
     * 是否重复
     */
    private Boolean isRecurring;

    /**
     * 重复类型
     */
    private RecurrenceRule recurrenceType;

    /**
     * 父事件id
     */
    private Long parentId;

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
    private Long ownerId;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 描述
     */
    private String describe;

    /**
     * 删除标志位
     */
    private Boolean delFlag;

    /**
     * 完成状态
     */
    private Boolean isComplete;

    /**
     * 难度
     */
    private Integer complexity;

    /**
     * 子事件
     */
    private List<ItemVO> subItems;

    /**
     * 用户
     */
    private List<User> users;
}
