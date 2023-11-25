package com.example.graduate.service.imlp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.graduate.common.RedisConstant;
import com.example.graduate.mapper.UserMapper;
import com.example.graduate.pojo.User;
import com.example.graduate.pojo.UserDetailsImlp;
import com.example.graduate.response.Code;
import com.example.graduate.response.R;
import com.example.graduate.service.UserService;
import com.example.graduate.utils.JwtUtil;
import com.example.graduate.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public R login(HashMap<String, Object> map) {
        //AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(map.get("account"), map.get("password"));
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果认证没通过，给出对应的提示
        if (Objects.isNull(authenticate)) throw new RuntimeException("登录失败");
        //如果认证通过了，使用userid生成一个jwt jwt存入ResponseResult返回
        UserDetailsImlp loginUser = (UserDetailsImlp) authenticate.getPrincipal();
        User user = loginUser.getUser();
        user.setPassword((String) map.get("password"));
        String userid = user.getId().toString();
        String jwt = JwtUtil.createJWT(userid, 60 * 60 * 1000 * 24 * 30L);
        Map<String, String> map1 = new HashMap<>();
        map1.put("token", jwt);
        map1.put("user", JSON.toJSONString(user));
        //把完整的用户信息存入redis  userid作为key
        redisCache.setCacheObject(RedisConstant.LOGIN_PREFIX + userid, loginUser);
        return new R("登录成功", map1, 200);
    }

    @Override
    public R logout() {
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImlp loginUser = (UserDetailsImlp) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();
        //删除redis中的值
        redisCache.deleteObject(RedisConstant.LOGIN_PREFIX + userid);
        return new R("OK", null, Code.SUCCESS);
    }

    @Transactional
    @Override
    public R signup(HashMap<String, Object> map) {
        String account = (String) map.get("account");
        String password = (String) map.get("password");
        if ("".equals(account.trim()) || "".equals(password.trim())) return new R("账号或密码不能为空", false, Code.FAIL);
        else if (account.trim().length() < 5 || password.trim().length() < 8 || password.trim().length() > 16)
            return new R("账号长度最少5位，密码长度最少8位最多16位", false, Code.FAIL);
        User user = new User();
        user.setAccount(account);
        if (userMapper.getSurvivalUserList(user) != null) return new R("账号已存在", false, Code.FAIL);
        password = passwordEncoder.encode(password);
        user.setCreateTime(new Date());
        user.setPassword(password);
        boolean one = userMapper.installOne(user);
        if (one) {
            user.setPassword(null);
            redisCache.setCacheObject(RedisConstant.SURVIVAL_PREFIX + user.getId(), user);
        }
        return one ? new R("注册成功", true, Code.SUCCESS) : new R("注册失败", false, Code.FAIL);
    }

    @Override
    public boolean updateInfo(User user) {
        return userMapper.update(user) == 1;
    }

    @Override
    public R weakBay(Long id) {
        return userMapper.updateFlag(id) == 1 ? new R("注销成功", true, Code.SUCCESS) : new R("注销失败", false, Code.FAIL);
    }

    @Transactional
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
    public List<User> findFriend(String account, String nickname) {
        User user = new User();
        user.setAccount(account);
        user.setNickname(nickname);
        return userMapper.getSurvivalUserList(user);
    }
}
