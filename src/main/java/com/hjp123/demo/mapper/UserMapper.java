package com.hjp123.demo.mapper;

import com.hjp123.demo.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    //插入带有id的Github用户数据
    void increaseGithubUser(User user);

    //插入不带id不带token的用户数据
    Long increaseUser(User user);

    //查询token数据
    User selectByToken(String token);

    //根据id查询用户数据
    User selectById(Long id);

    //根据id更新用户数据
    void updateUser(User user);

    //查询账号密码是否相同数据
    User selectNamePassword(String account,String password);

    //查询账号是否存在数据
    User selectName(String account);

    //插入不带id带token的用户数据
    void addUser(User user);

}
