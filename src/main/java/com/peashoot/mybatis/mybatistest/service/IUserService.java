package com.peashoot.mybatis.mybatistest.service;

import java.util.ArrayList;

import com.peashoot.mybatis.mybatistest.entity.User;

public interface IUserService {
    Boolean insert(User user);

    User get(int id);

    Boolean login(String account, String password);

    Boolean exist(String account);

    Boolean delete(String account);

    Boolean delete(int id);

    ArrayList<User> get();

    Boolean update(User user);
}