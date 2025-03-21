package com.bi.element.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bi.element.domain.po.Item;
import com.bi.element.domain.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User getOneUserByID(Long id);

    boolean installOne(User user);

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

    List<User> getUserByItem(Item item);

    User selectByUsername(String username);
}
