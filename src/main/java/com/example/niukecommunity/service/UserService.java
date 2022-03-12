package com.example.niukecommunity.service;

import com.example.niukecommunity.dao.UserMapper;
import com.example.niukecommunity.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findUserById(int id) {
        return userMapper.selectById(id);
    }

}
