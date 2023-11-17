package com.example.graduate.mapper;

import com.example.graduate.pojo.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    int addMessage(Message message);

    List<Message> getMessageList(Message message);

    Integer deleteMessage(Message message);

    Integer updateMessage(Message message);
}
