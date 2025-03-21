package com.bi.element.service;


import com.bi.element.domain.po.Item;
import com.bi.element.response.R;

import java.util.HashMap;
import java.util.List;

public interface ItemService {
    R deleteItem(Long id);

    R addItem(Item item);

    R changeItem(Item item);

    List<Item> getNowItem(HashMap<String, Object> element);
}
