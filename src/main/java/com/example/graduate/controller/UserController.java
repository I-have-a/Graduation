package com.example.graduate.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.graduate.common.BaseContext;
import com.example.graduate.common.RedisConstant;
import com.example.graduate.pojo.Message;
import com.example.graduate.pojo.User;
import com.example.graduate.response.Code;
import com.example.graduate.response.R;
import com.example.graduate.service.LoginService;
import com.example.graduate.service.MessageService;
import com.example.graduate.service.UserService;
import com.example.graduate.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    @Autowired
    RedisCache redisCache;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MessageService messageService;

    @PostMapping("login")
    @ResponseBody
    public R login(@RequestBody HashMap<String, Object> map) {
        return loginService.login(map);
    }

    @PostMapping("signup")
    @ResponseBody
    public R signup(@RequestBody HashMap<String, Object> map) {
        if (userService.signup(map)) {
            return new R("OK", true, Code.SUCCESS);
        } else {
            return new R("FAIL", false, Code.FAIL);
        }
    }

    @PostMapping("updateInfo")
    @ResponseBody
    public R updateInfo(@RequestBody HashMap<String, Object> map) throws ParseException {
        Long userID = BaseContext.getCurrentId();
        JSONObject object = redisCache.getCacheObject(RedisConstant.SURVIVAL_PREFIX + userID);
        User survivalUser;
        if (object != null) {
            String photo = (String) map.get("photo");
            String email = (String) map.get("email");
            String bothDay = (String) map.get("bothDay");
            String nickname = (String) map.get("nickname");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String phone = (String) map.get("phone");
            survivalUser = object.toJavaObject(User.class);
            if (photo != null) survivalUser.setPhoto(photo);
            if (bothDay != null) survivalUser.setBothDay(format.parse(bothDay));
            if (nickname != null) survivalUser.setNickname(nickname);
            if (email != null) survivalUser.setEmail(email);
            if (phone != null) survivalUser.setPhone(phone);
        } else return new R("用户不存在", null, Code.FAIL);
        boolean flag = userService.updateInfo(survivalUser);
        if (flag) {
            redisCache.deleteObject(RedisConstant.SURVIVAL_PREFIX + survivalUser.getId());
            redisCache.setCacheObject(RedisConstant.SURVIVAL_PREFIX + survivalUser.getId(), survivalUser);
            return new R("OK", survivalUser, Code.SUCCESS);
        } else {
            return new R("FAIL", null, Code.FAIL);
        }
    }

    @PostMapping("weakBay")
    @ResponseBody
    public R weakDeleteUser(Long id) {
        boolean flag = userService.weakBay(id);
        if (flag) {
            return new R("OK", true, Code.SUCCESS);
        } else {
            return new R("FAIL", false, Code.FAIL);
        }
    }

    @PostMapping("updatePassword")
    @ResponseBody
    public R updatePassword(@RequestBody HashMap<String, Object> map) {
        String priorPassword = (String) map.get("priorPassword");
        long l = BaseContext.getCurrentId();
        JSONObject cacheObject = redisCache.getCacheObject(RedisConstant.SURVIVAL_PREFIX + l);
        User user = cacheObject.toJavaObject(User.class);
        if (priorPassword.length() < 8 || priorPassword.length() > 16) {
            return new R("密码需要大于8位小于16位", null, Code.FAIL);
        }
        if (passwordEncoder.matches(priorPassword, user.getPassword())) {
            return new R("新密码不能和原密码相同", null, Code.FAIL);
        }
        priorPassword = passwordEncoder.encode(priorPassword);
        boolean flag = userService.updatePassword(priorPassword, l);
        if (flag) {
            return new R("", null, Code.SUCCESS);
        } else
            return new R("更新失败", null, Code.FAIL);
    }

    @PostMapping("goodBayFriend")
    @ResponseBody
    public R deleteFriends(@RequestBody HashMap<String, Object> map) {
        List<Integer> ids = (List<Integer>) map.get("ids");
        int flag = userService.deleteFriends(BaseContext.getCurrentId(), ids);
        if (flag >= 1) {
            return new R("已删除", null, Code.SUCCESS);
        } else {
            return new R("删除失败", null, Code.FAIL);
        }
    }

    @PostMapping("haiFriend")
    @ResponseBody
    public R addFriend(@RequestBody Message message) {
        message.setCuID(BaseContext.getCurrentId());
        return messageService.addMessage(message);
    }

    @PostMapping("whereFriend")
    @ResponseBody
    public R findFriend(@RequestBody HashMap<String, Object> map) {
        String text = (String) map.get("text");
        List<User> users = userService.findFriend(text, text);
        if (users.size() == 0) {
            return new R("查无此人，请重新输入", users, Code.FAIL);
        }
        return new R("获取成功", users, Code.SUCCESS);
    }

}
