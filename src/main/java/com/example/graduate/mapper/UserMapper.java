package com.example.graduate.mapper;

import com.example.graduate.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    User getOneUser(String acc);

    User getOneUserByID(Long id);

    boolean installOne(User user);

    Integer update(User user);

    List<User> getAllSurvivalUser();

    long updateFlag(Long id);

    int updatePassword(@Param("priorPassword") String priorPassword, @Param("id") Long id);

    List<User> getFriends(Long id);

    int deleteUU(Long id, List<Integer> ids);

    List<User> getUser(String account, String nickname);
}
