package com.example.graduate.service;

import com.example.graduate.pojo.Element;
import com.example.graduate.response.R;

import java.util.Date;

public interface ElementService {
    R deleteElement(Long id);

    R addElement(Element element);

    R changeElement(Element element);

    R getNowElement(Date date);
}
