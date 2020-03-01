package com.hjp123.demo.mapper;

import com.hjp123.demo.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    Long increaseGithubUser(User user);

    Long increaseUser(User user);

    User selectById(Long id);
}
