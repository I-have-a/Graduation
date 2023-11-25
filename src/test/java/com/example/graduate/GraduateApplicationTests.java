package com.example.graduate;

import com.example.graduate.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@SpringBootTest
class GraduateApplicationTests {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RedisCache redisCache;

    @Test
    void contextLoads() {
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
        System.out.println(passwordEncoder.matches("1234", "$2a$10$wc.YyuuPFtAwYQOTcs4J6O1GWGUqAWduT4cmEBufMs9zBPUrKv/8W"));
    }

}
