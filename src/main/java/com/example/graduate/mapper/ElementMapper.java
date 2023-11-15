package com.example.graduate.mapper;

import com.example.graduate.pojo.Element;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ElementMapper {
    List<Element> getElementListByUserID(Long userID);

    boolean updateDelFlag(Long id);

    Integer addElement(Element element, @Param("userID") Long userid);

    int updateElement(Element element);
}
