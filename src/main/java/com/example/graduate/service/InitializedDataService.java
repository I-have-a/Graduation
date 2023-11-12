package com.example.graduate.service;

import com.alibaba.fastjson.JSONObject;
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
        System.out.println("启动类被运行加载会调用我");
        JSONObject cacheObject = redisCache.getCacheObject(RedisConstant.SURVIVAL_PREFIX + 1);
        if (cacheObject == null) {
            //不存在就创建
            List<User> users = userMapper.getAllSurvivalUser();
            users.forEach(user -> {
                redisCache.setCacheObject(RedisConstant.SURVIVAL_PREFIX + user.getId(), user);
            });
        }
        System.out.println("启动类被运行加载会调用我");
    }
}
