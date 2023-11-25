package com.example.graduate.controller;

import com.example.graduate.common.BaseContext;
import com.example.graduate.pojo.Element;
import com.example.graduate.response.Code;
import com.example.graduate.response.R;
import com.example.graduate.service.ElementService;
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
        map.put("pStartTime", date);
        map.put("uid", BaseContext.getCurrentId());
        map.put("deleteFlag", true);
        List<Element> nowElement = elementService.getNowElement(map);
        return nowElement != null ? new R("获取成功", nowElement, Code.SUCCESS) : new R("获取失败", null, Code.FAIL);
    }

    @GetMapping("weakElement")
    @ResponseBody
    public R bay(Long elementID) {
        return elementService.deleteElement(elementID);
    }

    @PostMapping("add")
    @ResponseBody
    public R add(@RequestBody Element element) {
        return elementService.addElement(element);
    }

    @PostMapping("change")
    @ResponseBody
    public R update(@RequestBody Element element) {
        return elementService.changeElement(element);
    }

}
