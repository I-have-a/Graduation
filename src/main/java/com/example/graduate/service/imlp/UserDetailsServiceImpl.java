package com.example.graduate.service.imlp;

import com.example.graduate.mapper.UserMapper;
import com.example.graduate.pojo.User;
import com.example.graduate.pojo.UserDetailsImlp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User p = new User();
        p.setAccount(username);
        User user = userMapper.getUserList(p).get(0);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        List<User> friends = userMapper.getFriends(user.getId());
        user.setFriends(friends);
        return new UserDetailsImlp(user);
    }
}
