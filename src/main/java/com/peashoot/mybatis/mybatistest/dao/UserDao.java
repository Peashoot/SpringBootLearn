package com.peashoot.mybatis.mybatistest.dao;

import java.util.ArrayList;

import com.peashoot.mybatis.mybatistest.entity.User;

import org.apache.ibatis.annotations.Param;

public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    Boolean exist(String account);

    int updateByPrimaryKey(User record);

    int checkAccountAndPassword(@Param("account") String account, @Param("password") String password);

    int deleteByAccount(String account);

    ArrayList<User> all();
}