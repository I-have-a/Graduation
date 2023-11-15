package com.example.graduate.service.imlp;

import com.example.graduate.mapper.MessageMapper;
import com.example.graduate.mapper.UserMapper;
import com.example.graduate.pojo.Message;
import com.example.graduate.response.Code;
import com.example.graduate.response.R;
import com.example.graduate.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    UserMapper userMapper;

    @Transactional
    @Override
    public R addMessage(Message message) {
        if (Objects.equals(message.getCuID(), message.getRuID())) {
            return new R("发送者和接收者不能相同", null, Code.FAIL);
        }
        if (messageMapper.getMessageList(message).size() > 0) {
            return new R("您已发过申请了，请稍等", null, Code.FAIL);
        }
        if (userMapper.getOneUserByID(message.getRuID()).getStatus()) {
            return new R("哦豁，这个用户寄了", null, Code.FAIL);
        }
        if (messageMapper.addMessage(message) == 1) {
            return new R("发送成功", true, Code.SUCCESS);
        }
        return new R("未知错误", null, Code.FAIL);
    }
}
