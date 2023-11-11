package com.example.graduate.service;

import com.example.graduate.response.R;

import java.util.HashMap;

public interface LoginService {
    R login(HashMap<String, Object> map);

    R logout();
}
