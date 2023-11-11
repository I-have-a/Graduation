package com.example.graduate.mapper;

import com.example.graduate.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User getOneUser(String acc);
}
