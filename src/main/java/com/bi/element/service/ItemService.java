package com.bi.element.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.bi.element.domain.po.Item;
import com.bi.element.domain.vo.ItemVO;

import java.util.List;

public interface ItemService extends IService<Item> {
    Boolean deleteItem(Long id);

    Boolean addItem(ItemVO item);

    Boolean changeItem(ItemVO item);

    List<Item> getItem(ItemVO itemVO);
}
