package com.example.graduate.mapper;

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
     * 和getUserList的区别在于这个方法会获得密码。
     */
    List<User> getAllSurvivalUser();

    /**
     * 没有生日范围查询
     */
    List<User> getUserList(User user);

    long updateFlag(Long id);

    int updatePassword(@Param("priorPassword") String priorPassword, @Param("id") Long id);

    List<User> getFriends(Long id);

    int deleteUU(Long id, List<Integer> ids);
}
