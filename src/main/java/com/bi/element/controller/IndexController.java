package com.bi.element.controller;

import com.bi.element.response.Code;
import com.bi.element.response.R;
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
