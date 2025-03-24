package com.bi.element.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

@Data
@TableName("tb_user_event_order")
public class UserEventOrder {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    // 用户id
    private Long userId;
    // 事件id
    private Long itemId;
    private Long eventInstanceId;
    // 排序
    private Integer orderNum;

    private Long parentId;

    @Version
    private Integer version;
}
