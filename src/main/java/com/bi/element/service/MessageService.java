package com.bi.element.service;


import com.bi.element.pojo.Message;
import com.bi.element.response.R;

public interface MessageService {
    R addMessage(Message message);
}
