package com.example.graduate.mapper;

import com.example.graduate.pojo.Element;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ElementMapper {
    List<Element> getElementListByUserID(Long userID);

    List<Element> getElements(Element element);

    boolean updateDelFlag(Long id);

    Integer addElement(Element element, @Param("userID") Long userid);

    int updateElement(Element element);

    /**
     * ========================================
     * U_E部分，增删Element时，U_E也要协同增删    ||
     * 修改时不更新                            ||
     * ========================================
     */

    Integer addUE(@Param("uid") Long currentId, @Param("eid") Integer elementID);

    Integer deleteUE(@Param("uid") Long currentId, @Param("eid") Integer elementID);

    HashMap selectUE(@Param("uid") Long currentId, @Param("eid") Integer elementID);
}
