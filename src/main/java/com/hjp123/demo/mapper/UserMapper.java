package com.hjp123.demo.mapper;

import com.hjp123.demo.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void increaseGithubUser(User user);

    Long increaseUser(User user);

    User selectByToken(String token);

    User selectById(Long id);

    void updateUser(User user);
}
