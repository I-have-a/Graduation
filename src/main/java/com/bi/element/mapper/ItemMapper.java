package com.bi.element.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bi.element.domain.po.Item;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ItemMapper extends BaseMapper<Item> {
    @Insert("insert into tb_item_shares(user_id,item_id) values(#{userid},#{elementID})")
    int addUE(Long userid, Integer elementID);
}
