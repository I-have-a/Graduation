package com.example.graduate.service;

import com.example.graduate.common.RedisConstant;
import com.example.graduate.mapper.UserMapper;
import com.example.graduate.pojo.User;
import com.example.graduate.utils.RedisCache;
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
        //TODO 后期启动项目，ES刷新更改
        List<User> users = userMapper.getSurvivalUserList(null);
        for (User user : users)
            redisCache.setCacheObject(RedisConstant.SURVIVAL_PREFIX + user.getId(), user);
        System.out.println("启动类被运行加载会调用我");
    }
}
