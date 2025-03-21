package com.bi.element.annotation.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bi.element.annotation.UniqueUsername;
import com.bi.element.domain.po.User;
import com.bi.element.mapper.UserMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount, username);
        return userMapper.exists(wrapper);
    }
}
