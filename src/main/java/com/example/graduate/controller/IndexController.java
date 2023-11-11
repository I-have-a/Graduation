package com.example.graduate.controller;

import com.example.graduate.response.Code;
import com.example.graduate.response.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController {
    @GetMapping("/test")
    @ResponseBody
    public R index() {
        return new R("OK", "hgfghfhj", Code.SUCCESS);
    }
}
