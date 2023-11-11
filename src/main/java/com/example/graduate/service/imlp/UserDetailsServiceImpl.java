package com.example.graduate.service.imlp;

import com.example.graduate.mapper.UserMapper;
import com.example.graduate.pojo.User;
import com.example.graduate.pojo.UserDetailsImlp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getOneUser(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        return new UserDetailsImlp(user);
    }
}
