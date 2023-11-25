package com.example.graduate.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.graduate.common.BaseContext;
import com.example.graduate.common.RedisConstant;
import com.example.graduate.pojo.Message;
import com.example.graduate.pojo.User;
import com.example.graduate.response.Code;
import com.example.graduate.response.R;
import com.example.graduate.service.MessageService;
import com.example.graduate.service.UserService;
import com.example.graduate.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

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
        return userService.login(map);
    }

    @PostMapping("signup")
    @ResponseBody
    public R signup(@RequestBody HashMap<String, Object> map) {
        return userService.signup(map);
    }

    @GetMapping("logout")
    @ResponseBody
    public R logout() {
        return userService.logout();
    }

    @PostMapping("weakBay")
    @ResponseBody
    public R weakDeleteUser(Long id) {
        return userService.weakBay(id);
    }

    @Transactional
    @PostMapping("updateInfo")
    @ResponseBody
    public R updateInfo(@RequestBody User user) {
        Long userID = BaseContext.getCurrentId();
        User survivalUser = null;
        boolean flag = userService.updateInfo(user);
        if (flag) {
            JSONObject object = redisCache.getCacheObject(RedisConstant.SURVIVAL_PREFIX + userID);
            if (object != null) {
                survivalUser = object.toJavaObject(User.class);
                if (user.getNickname() != null) survivalUser.setNickname(user.getNickname());
                if (user.getEmail() != null) survivalUser.setEmail(user.getEmail());
                if (user.getPhone() != null) survivalUser.setPhone(user.getPhone());
                if (user.getPhoto() != null) survivalUser.setPhoto(user.getPhoto());
                if (user.getBothDay() != null) survivalUser.setBothDay(user.getBothDay());
                Object o = redisCache.updateObject(RedisConstant.SURVIVAL_PREFIX, survivalUser);
                if (o == null) return new R("修改失败", null, Code.FAIL);
            }
            return new R("修改成功", survivalUser, Code.SUCCESS);
        } else
            return new R("修改失败", null, Code.FAIL);
    }

    @PostMapping("updatePassword")
    @ResponseBody
    public R updatePassword(@RequestBody HashMap<String, Object> map) {
        String priorPassword = (String) map.get("priorPassword");
        long l = BaseContext.getCurrentId();
        JSONObject cacheObject = redisCache.getCacheObject(RedisConstant.SURVIVAL_PREFIX + l);
        User user = cacheObject.toJavaObject(User.class);
        if (priorPassword.length() < 8 || priorPassword.length() > 16)
            return new R("密码需要大于8位小于16位", null, Code.FAIL);
        if (passwordEncoder.matches(priorPassword, user.getPassword()))
            return new R("新密码不能和原密码相同", null, Code.FAIL);
        priorPassword = passwordEncoder.encode(priorPassword);
        boolean flag = userService.updatePassword(priorPassword, l);
        if (flag) return new R("更新成功", null, Code.SUCCESS);
        return new R("更新失败", null, Code.FAIL);
    }

    @PostMapping("goodBayFriend")
    @ResponseBody
    public R deleteFriends(@RequestBody HashMap<String, Object> map) {
        List<Integer> ids = (List<Integer>) map.get("ids");
        int flag = userService.deleteFriends(BaseContext.getCurrentId(), ids);
        if (flag >= 1) return new R("已删除", null, Code.SUCCESS);
        return new R("删除失败", null, Code.FAIL);
    }

    @PostMapping("haiFriend")
    @ResponseBody
    public R addFriend(@RequestBody Message message) {
        message.setCuID(BaseContext.getCurrentId());
        message.setStatus(0);
        return messageService.addMessage(message);
    }

    @PostMapping("whereFriend")
    @ResponseBody
    public R findFriend(@RequestBody HashMap<String, Object> map) {
        String text = (String) map.get("text");
        List<User> users = userService.findFriend(text, text);
        if (users.size() == 0) return new R("查无此人，请重新输入", users, Code.FAIL);
        return new R("获取成功", users, Code.SUCCESS);
    }
}
