package com.example.graduate;

import com.alibaba.fastjson2.JSON;
import com.example.graduate.pojo.User;
import com.example.graduate.service.LoginService;
import com.example.graduate.service.UserService;
import com.example.graduate.utils.JwtUtil;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
class GraduateApplicationTests {

    @Autowired
    LoginService loginService;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        User user = new User();
        user.setAccount("admin");
        user.setId(1L);
        user.setPassword("$10$npv5JSeFR6/wLz8BBMmSBOMb8byg2eyfK4/vvoBk3RKtTLBhIhcpy");
        user.setStatus(true);
        user.setBothDay(new Date());
        user.setEmail("gjayhsgjksadghjk");
        user.setNickname("守黄昏");
        user.setPhone("askjdhjk");
        user.setPhoto("ghjdasjkhas");
        System.out.println(JSON.toJSONString(user));
        System.out.println(JwtUtil.createJWT(JSON.toJSONString(user)));
    }

    @Test
    void testRides(){
//        String s = stringRedisTemplate.opsForValue().get("login:1");
//        User user = JSON.parseObject(s, User.class);
//        System.out.println(user);
    }

}
