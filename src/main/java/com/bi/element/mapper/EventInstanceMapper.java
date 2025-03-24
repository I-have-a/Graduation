package com.bi.element.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bi.element.domain.po.EventInstance;
import com.bi.element.domain.po.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EventInstanceMapper extends BaseMapper<EventInstance> {
    @Select("SELECT ti.*, ei.event_date FROM tb_item ti " +
            "JOIN tb_event_instances ei ON ti.id = ei.item_id " +
            "WHERE ei.event_date = #{date}")
    List<Item> selectRecurringEventsByDate(@Param("date") LocalDate date);
}
