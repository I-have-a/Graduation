package com.bi.element.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bi.element.domain.po.Item;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ItemMapper extends BaseMapper<Item> {
    @Insert("insert into tb_item_shares(user_id,item_id) values(#{userid},#{elementID})")
    int addUE(Long userid, Integer elementID);

    @Select("SELECT ti.* FROM tb_item ti " +
            "LEFT JOIN tb_event_instances ei ON ti.id = ei.item_id " +
            "WHERE ti.event_date = #{date} OR ei.event_date = #{date}")
    Page<Item> selectByDate(Page<Item> page, @Param("date") LocalDate date);

    @Select("SELECT * FROM tb_item " +
            "WHERE start_date <= #{end} AND end_date >= #{start}")
    List<Item> selectByDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Select("SELECT ti.*, ueo.order_num FROM tb_item ti " +
            "LEFT JOIN tb_item_shares tis ON ti.id = tis.item_id " +
            "LEFT JOIN tb_user_event_order ueo ON ti.id = ueo.item_id AND ueo.user_id = #{userId} " +
            "WHERE ti.owner_id = #{userId} OR tis.user_id = #{userId} " +
            "ORDER BY ueo.order_num")
    List<Item> selectUserEvents(@Param("userId") Long userId);

    @Select("SELECT ti.*, ueo.order_num " +
            "FROM tb_item ti " +
            "LEFT JOIN tb_user_event_order ueo ON ti.id = ueo.item_id AND ueo.user_id = #{userId} " +
            "WHERE (ti.owner_id = #{userId} " +
            "OR EXISTS (SELECT 1 FROM tb_item_shares tis WHERE tis.item_id = ti.id AND tis.user_id = #{userId})) " +
            "AND ti.parent_id IS NULL " + // 仅查询根事件
            "ORDER BY ueo.order_num ")
    List<Item> selectRootItems(@Param("userId") Long userId);

    @Select("SELECT ti.*, ueo.order_num " +
            "FROM tb_item ti " +
            "LEFT JOIN tb_user_event_order ueo ON ti.id = ueo.item_id AND ueo.user_id = #{userId} " +
            "WHERE ti.parent_id = #{parentId} " +
            "ORDER BY ueo.order_num ")
    List<Item> selectChildren(@Param("userId") Long userId, @Param("parentId") Long parentId);
}
