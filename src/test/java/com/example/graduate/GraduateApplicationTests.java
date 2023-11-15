package com.example.graduate;

import com.alibaba.fastjson2.JSON;
import com.example.graduate.pojo.User;
import com.example.graduate.service.LoginService;
import com.example.graduate.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    void testRides() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.parse("2003-10-27 02:40:12"));
//        String s = stringRedisTemplate.opsForValue().get("login:1");
//        User user = JSON.parseObject(s, User.class);
//        System.out.println(user);
    }

    @Test
    void testPassword() {
        System.out.println(passwordEncoder.encode("1234"));
        System.out.println(passwordEncoder.upgradeEncoding("$2a$10$XDmuOefGkHlXgGq3W0mhl.z19ILTK53XcYL2iAQVEIFt2UuZy3Cw6"));
    }

}
