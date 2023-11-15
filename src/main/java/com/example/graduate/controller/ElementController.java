package com.example.graduate.controller;

import com.example.graduate.pojo.Element;
import com.example.graduate.response.R;
import com.example.graduate.service.ElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/element")
public class ElementController {

    @Autowired
    ElementService elementService;

    @GetMapping("/come")
    @ResponseBody
    public R come() {
        return elementService.getList();
    }

    @GetMapping("/weakElement")
    @ResponseBody
    public R bay(Long elementID) {
        return elementService.deleteElement(elementID);
    }

    @PostMapping("/add")
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
