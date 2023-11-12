package com.example.graduate.service.imlp;

import com.alibaba.fastjson.JSONObject;
import com.example.graduate.common.RedisConstant;
import com.example.graduate.mapper.UserMapper;
import com.example.graduate.pojo.User;
import com.example.graduate.pojo.UserDetailsImlp;
import com.example.graduate.service.UserService;
import com.example.graduate.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private RedisCache redisCache;

    @Override
    public boolean signup(HashMap<String, Object> map) {
        String account = (String) map.get("account");
        String password = (String) map.get("password");
        if ("".equals(account) || "".equals(password)) return false;
        else if (account.length() < 5 || password.length() < 8) return false;
        if (userMapper.getOneUser(account) != null) return false;
        password = passwordEncoder.encode(password);
        User user = new User();
        user.setCreateTime(new Date());
        user.setAccount(account);
        user.setPassword(password);
        boolean one = userMapper.installOne(user);
        if (one) {
            user.setPassword(null);
            redisCache.setCacheObject(RedisConstant.SURVIVAL_PREFIX + user.getId(), user);
        }
        return one;
    }

    @Override
    public boolean updateInfo(User user) {
        return userMapper.update(user) == 1;
    }

    @Override
    public boolean weakBay(Long id) {
        return userMapper.updateFlag(id) == 1;
    }

    @Override
    public boolean updatePassword(String priorPassword, Long id) {
        if (userMapper.updatePassword(priorPassword, id) == 1) {
            JSONObject loginObject = redisCache.getCacheObject(RedisConstant.LOGIN_PREFIX + id);
            JSONObject survivalObject = redisCache.getCacheObject(RedisConstant.SURVIVAL_PREFIX + id);
            UserDetailsImlp userDetailsImlp = loginObject.toJavaObject(UserDetailsImlp.class);
            User user = survivalObject.toJavaObject(User.class);
            userDetailsImlp.setUser(userMapper.getOneUserByID(id));
            user.setPassword(priorPassword);
            Object o = redisCache.updateObject(RedisConstant.LOGIN_PREFIX + id, userDetailsImlp);
            Object o1 = redisCache.updateObject(RedisConstant.SURVIVAL_PREFIX + id, user);
            return o != null && o1 != null;
        }
        return false;
    }

    @Override
    public int deleteFriends(Long currentId, List<Integer> ids) {
        return userMapper.deleteUU(currentId, ids);
    }

    @Override
    public List<User> findFriend(HashMap<String, Object> map) {
        List<User> users = userMapper.getUser(map);
        return null;
    }
}
