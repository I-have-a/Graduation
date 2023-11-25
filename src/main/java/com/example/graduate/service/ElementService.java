package com.example.graduate.service;

import com.example.graduate.pojo.Element;
import com.example.graduate.response.R;

import java.util.HashMap;
import java.util.List;

public interface ElementService {
    R deleteElement(Long id);

    R addElement(Element element);

    R changeElement(Element element);

    List<Element> getNowElement(HashMap<String, Object> element);
}
