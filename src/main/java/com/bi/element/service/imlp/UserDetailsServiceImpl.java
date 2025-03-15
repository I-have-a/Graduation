package com.bi.element.service.imlp;

import com.bi.element.mapper.UserMapper;
import com.bi.element.pojo.User;
import com.bi.element.pojo.UserDetailsImlp;
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
        User user = userMapper.getLoginUser(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        List<User> friends = userMapper.getFriends(user.getId());
        user.setFriends(friends);
        return new UserDetailsImlp(user);
    }
}
