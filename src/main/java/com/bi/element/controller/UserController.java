package com.bi.element.controller;

import com.bi.element.domain.po.LoginUser;
import com.bi.element.domain.po.Message;
import com.bi.element.domain.po.User;
import com.bi.element.domain.validation.Add;
import com.bi.element.domain.validation.Login;
import com.bi.element.domain.validation.Update;
import com.bi.element.domain.vo.UserVO;
import com.bi.element.response.Code;
import com.bi.element.response.R;
import com.bi.element.service.MessageService;
import com.bi.element.service.UserService;
import com.bi.element.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    private final MessageService messageService;

    @PostMapping("login")
    public R login(@Validated(Login.class) @RequestBody UserVO user, HttpServletRequest request) {
        String login = userService.login(user, request);
        return R.ok(login);
    }

    @GetMapping("checkToken")
    public R checkToken() {
        return R.ok("校验通过");
    }

    @PostMapping("signup")
    public R signup(@Validated(Add.class) @RequestBody UserVO user) {
        return userService.signup(user) ? R.ok("注册成功") : R.error("注册失败");
    }

    @PostMapping("logoff")
    public R logoff(Long id) {
        return userService.logoff(id) ? R.ok("注销成功") : R.error("注销失败");
    }

    @GetMapping("findUser")
    public R findUser(String account, String nickname) {
        List<User> users = userService.findUser(account, nickname);
        return !users.isEmpty() ? R.ok("获取成功", users) : R.error("获取失败");
    }

    @PostMapping("updateInfo")
    public R updateInfo(@Validated(Update.class) @RequestBody UserVO user) {
        return userService.updateInfo(user) ? R.ok("修改成功") : R.error("修改失败");
    }

    @PostMapping("updatePassword")
    public R updatePassword(String oldPwd, String newPwd) {
        long l = SecurityUtils.getUserId();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String password = loginUser.getPassword();

        if (!SecurityUtils.matchesPassword(oldPwd, password))
            return R.error("修改密码失败，旧密码错误");
        if (Objects.equals(oldPwd, newPwd))
            return R.error("新密码不能和旧密码相同");

        boolean flag = userService.updatePassword(newPwd, l);

        if (flag) return R.ok("更新成功");
        return R.error("更新失败");
    }

    @PostMapping("goodBayFriend")
    public R deleteFriends(@RequestBody List<Integer> ids) {
        int flag = userService.deleteFriends(SecurityUtils.getUserId(), ids);
        if (flag >= 1) return R.ok("已删除", null, Code.SUCCESS);
        return R.error("删除失败", null, Code.FAIL);
    }

    @PostMapping("postMessage")
    public R addFriend(@RequestBody Message message) {
        message.setCuID(SecurityUtils.getUserId());
        message.setStatus(0);
        return messageService.addMessage(message);
    }

    @PostMapping("whereFriend")
    public R findFriend(@RequestBody String text) {
        List<User> users = userService.findFriend(text, text);
        if (users.isEmpty()) return R.error("查无此人，请重新输入", users, Code.FAIL);
        return R.ok("获取成功", users, Code.SUCCESS);
    }
}
