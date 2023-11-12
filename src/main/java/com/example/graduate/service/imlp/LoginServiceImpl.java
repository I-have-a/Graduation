package com.example.graduate.service.imlp;

import com.example.graduate.common.RedisConstant;
import com.example.graduate.pojo.UserDetailsImlp;
import com.example.graduate.response.R;
import com.example.graduate.service.LoginService;
import com.example.graduate.utils.JwtUtil;
import com.example.graduate.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public R login(HashMap<String, Object> map) {
        //AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(map.get("account"), map.get("password"));
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果认证没通过，给出对应的提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }
        //如果认证通过了，使用userid生成一个jwt jwt存入ResponseResult返回
        UserDetailsImlp loginUser = (UserDetailsImlp) authenticate.getPrincipal();
        String userid = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userid, 60 * 60 * 1000 * 24 * 30L);
        Map<String, String> map1 = new HashMap<>();
        map1.put("token", jwt);
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
        redisCache.deleteObject("login:" + userid);
        return new R("OK", null, 200);
    }
}
