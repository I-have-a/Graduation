package com.bi.element.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDate;
import java.util.List;

@Data
@TableName("tb_event_instances")
public class EventInstance implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 事件id
     */
    private Long itemId;

    /**
     * 事件日期
     */
    private LocalDate eventDate;

    private List<EventInstance> subInstances;

    /**
     * 是否完成
     */
    private Boolean isCompleted;
}
