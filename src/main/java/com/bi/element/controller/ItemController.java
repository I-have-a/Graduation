package com.bi.element.controller;

import com.bi.element.domain.po.Item;
import com.bi.element.response.R;
import com.bi.element.service.ItemService;
import com.bi.element.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/element")
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("now")
    @ResponseBody
    public R come(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("date", date);
        map.put("uid", SecurityUtils.getUserId());
        map.put("deleteFlag", true);
        List<Item> nowItem = itemService.getNowItem(map);
        return nowItem != null ? R.ok("获取成功", nowItem) : R.error("获取失败");
    }

    @GetMapping("weakElement")
    @ResponseBody
    public R bay(Long elementID) {
        return itemService.deleteItem(elementID);
    }

    @PostMapping("add")
    @ResponseBody
    public R add(@RequestBody Item item) {
        return itemService.addItem(item);
    }

    @PostMapping("change")
    @ResponseBody
    public R update(@RequestBody Item item) {
        return itemService.changeItem(item);
    }
}
