package com.bi.element.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bi.element.domain.po.User;
import com.bi.element.domain.vo.UserVO;
import com.bi.element.response.R;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {
    Map<String,Object> login(User user, HttpServletRequest request);

    Boolean signup(UserVO user);

    boolean updateInfo(User user);

    R weakBay(Long id);

    boolean updatePassword(String priorPassword, Long id);

    int deleteFriends(Long currentId, List<Integer> ids);

    List<User> findFriend(String text, String text1);

    List<User> findUser(String account, String nickname);

    Integer updateUserProfile(User user);
}
