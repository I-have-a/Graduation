package com.bi.element.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bi.element.domain.po.UserEventOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;

@Mapper
public interface UserEventOrderMapper extends BaseMapper<UserEventOrder> {

    @Select("SELECT ueo.order_num " +
            "FROM tb_user_event_order ueo " +
            "LEFT JOIN tb_item i ON ueo.item_id = i.id " +
            "WHERE ueo.user_id = #{userId} and DATE(i.create_time) = #{date} " +
            "ORDER BY ueo.order_num DESC")
    int selectUserDayOrder(@Param("userId") Long userId, @Param("date") String date);

    Integer selectMaxOrderNumByUser(@Param("userId") Long userId,
                                    @Param("parentId") Long parentId,
                                    @Param("eventDate") LocalDate eventDate);
}
