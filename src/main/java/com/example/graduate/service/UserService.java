package com.example.graduate.service;

import com.example.graduate.pojo.User;
import com.example.graduate.response.R;

import java.util.HashMap;
import java.util.List;

public interface UserService {
    R login(HashMap<String, Object> map);

    R logout();

    R signup(HashMap<String, Object> map);

    boolean updateInfo(User user);

    R weakBay(Long id);

    boolean updatePassword(String priorPassword, Long id);

    int deleteFriends(Long currentId, List<Integer> ids);

    List<User> findFriend(String text, String text1);
}
