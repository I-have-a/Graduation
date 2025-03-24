package com.bi.element.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bi.element.domain.po.User;
import com.bi.element.domain.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserService extends IService<User> {
    String login(UserVO user, HttpServletRequest request);

    Boolean signup(UserVO user);

    Boolean updateInfo(UserVO user);

    Boolean logoff(Long id);

    Boolean updatePassword(String priorPassword, Long id);

    Integer deleteFriends(Long currentId, List<Integer> ids);

    List<User> findFriend(String text, String text1);

    List<User> findUser(String account, String nickname);
}
