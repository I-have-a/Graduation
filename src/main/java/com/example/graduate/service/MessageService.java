package com.example.graduate.service;

import com.example.graduate.pojo.Message;
import com.example.graduate.response.R;

public interface MessageService {
    R addMessage(Message message);
}
