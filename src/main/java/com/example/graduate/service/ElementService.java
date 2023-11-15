package com.example.graduate.service;

import com.example.graduate.pojo.Element;
import com.example.graduate.response.R;

public interface ElementService {
    R getList();

    R deleteElement(Long id);

    R addElement(Element element);

    R changeElement(Element element);
}
