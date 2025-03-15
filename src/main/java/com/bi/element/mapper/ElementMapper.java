package com.bi.element.mapper;

import com.bi.element.pojo.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ElementMapper {
    List<Item> getElementListByUserID(Long userID);

    List<Item> getElements(HashMap<String, Object> element);

    boolean updateDelFlag(Long id);

    Integer addElement(Item item, @Param("userID") Long userid);

    int updateElement(Item item);

    /**
     * ========================================
     * U_E部分，增删Element时，U_E也要协同增删    ||
     * 修改时不更新                            ||
     * ========================================
     */

    Integer addUE(@Param("uid") Long currentId, @Param("eid") Integer elementID);

    Integer deleteUE(@Param("uid") Long currentId, @Param("eid") Integer elementID);

    HashMap selectUE(HashMap<String, Long> map);

    List<Item> getSubElementList(Long id);
}
