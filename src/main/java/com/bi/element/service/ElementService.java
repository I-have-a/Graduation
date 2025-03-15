package com.bi.element.service;


import com.bi.element.pojo.Item;
import com.bi.element.response.R;

import java.util.HashMap;
import java.util.List;

public interface ElementService {
    R deleteElement(Long id);

    R addElement(Item item);

    R changeElement(Item item);

    List<Item> getNowElement(HashMap<String, Object> element);
}
