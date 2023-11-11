package com.example.graduate.controller;

import com.example.graduate.response.R;
import com.example.graduate.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    @ResponseBody
    public R login(@RequestBody HashMap<String, Object> map) {
        return loginService.login(map);
    }
}
