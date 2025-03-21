package com.bi.element.service;

import com.bi.element.common.RedisConstant;
import com.bi.element.mapper.UserMapper;
import com.bi.element.domain.po.User;
import com.bi.element.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InitializedDataService {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private UserMapper userMapper;

    public void initProductInfo() {
        //TODO 后期启动项目
        List<User> users = userMapper.getSurvivalUserList(null);
        for (User user : users)
            redisCache.setCacheObject(RedisConstant.SURVIVAL_USER + user.getId(), user);
        System.out.println("启动类被运行加载会调用我");
    }
}
