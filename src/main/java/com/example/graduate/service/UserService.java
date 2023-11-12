package com.example.graduate.service;

import com.example.graduate.pojo.User;

import java.util.HashMap;
import java.util.List;

public interface UserService {

    boolean signup(HashMap<String, Object> map);

    boolean updateInfo(User user);

    boolean weakBay(Long id);

    boolean updatePassword(String priorPassword, Long id);

    int deleteFriends(Long currentId, List<Integer> ids);

    List<User> findFriend(HashMap<String, Object> map);
}
