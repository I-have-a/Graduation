package com.bi.element.service.impl;

import com.bi.element.domain.po.Message;
import com.bi.element.domain.statusEnum.UserStatus;
import com.bi.element.mapper.MessageMapper;
import com.bi.element.mapper.UserMapper;
import com.bi.element.response.Code;
import com.bi.element.response.R;
import com.bi.element.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;

    private final UserMapper userMapper;

    @Transactional
    @Override
    public R addMessage(Message message) {
        if (Objects.equals(message.getCuID(), message.getRuID())) {
            throw new RuntimeException("发送者和接收者不能相同");
        }
        if (!messageMapper.getMessageList(message).isEmpty()) {
            throw new RuntimeException("您已发过申请了，请稍等");
        }
        if (userMapper.getOneUserByID(message.getRuID()).getStatus() != UserStatus.NORMAL) {
            throw new RuntimeException("哦豁，这个用户寄了");
        }
        if (messageMapper.addMessage(message) != 1) {
            throw new RuntimeException("添加失败");
        }
        return new R("发送成功", true, Code.SUCCESS);
    }
}
