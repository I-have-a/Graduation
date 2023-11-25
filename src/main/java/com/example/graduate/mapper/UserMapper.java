package com.example.graduate.mapper;

import com.example.graduate.pojo.Element;
import com.example.graduate.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    User getOneUserByID(Long id);

    boolean installOne(User user);

    Integer update(User user);

    /**
     * 没有生日范围查询
     */
    List<User> getSurvivalUserList(User user);

    long updateFlag(Long id);

    Integer updatePassword(@Param("priorPassword") String priorPassword, @Param("id") Long id);

    List<User> getFriends(Long id);

    Integer deleteUU(Long id, List<Integer> ids);

    Integer addUU(@Param("id") Long id, @Param("fID") Long fID);

    User getLoginUser(String account);

    List<User> getUserByElement(Element element);
}
