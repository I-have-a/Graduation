package com.bi.element.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_item_shares")
public class ItemShare {
    @TableId
    private Long id;
    private Long todoItemId;
    private Long userId;
    private String permission;
}
