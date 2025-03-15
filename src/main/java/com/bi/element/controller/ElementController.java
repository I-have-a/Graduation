package com.bi.element.controller;

import com.bi.element.common.BaseContext;
import com.bi.element.pojo.Item;
import com.bi.element.response.Code;
import com.bi.element.response.R;
import com.bi.element.service.ElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/element")
public class ElementController {

    @Autowired
    ElementService elementService;

    @GetMapping("now")
    @ResponseBody
    public R come(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("date", date);
        map.put("uid", BaseContext.getCurrentId());
        map.put("deleteFlag", true);
        List<Item> nowItem = elementService.getNowElement(map);
        return nowItem != null ? new R("获取成功", nowItem, Code.SUCCESS) : new R("获取失败", null, Code.FAIL);
    }

    @GetMapping("weakElement")
    @ResponseBody
    public R bay(Long elementID) {
        return elementService.deleteElement(elementID);
    }

    @PostMapping("add")
    @ResponseBody
    public R add(@RequestBody Item item) {
        return elementService.addElement(item);
    }

    @PostMapping("change")
    @ResponseBody
    public R update(@RequestBody Item item) {
        return elementService.changeElement(item);
    }

}
