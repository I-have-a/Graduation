package com.example.graduate.mapper;

import com.example.graduate.pojo.SubElement;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubElementMapper {

    List<SubElement> getSubElementList(Long eID);

    boolean addAllSubElement(List<SubElement> subElements, Long id);
}
