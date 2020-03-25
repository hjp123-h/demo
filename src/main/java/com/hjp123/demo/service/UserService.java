package com.hjp123.demo.service;

import com.hjp123.demo.bean.User;
import com.hjp123.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserService {
    @Autowired
    private UserMapper userMapper;

    //根据id查询用户
    public User findUserById(Long id){
        return userMapper.selectById(id);
    }

}
