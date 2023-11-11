package com.example.graduate.service.imlp;

import com.example.graduate.mapper.UserMapper;
import com.example.graduate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

}
