package com.peashoot.mybatis.mybatistest.service;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;

import com.peashoot.mybatis.mybatistest.entity.User;
import com.peashoot.mybatis.mybatistest.util.MD5;
import com.peashoot.mybatis.mybatistest.dao.UserDao;

import org.springframework.stereotype.Service;

@Service("userService")
public class UserService implements IUserService {
    @Resource
    private UserDao userDao;

    public Boolean insert(User user) {
        if (userDao.exist(user.getAccount())) {
            return false;
        }
        try {
            user.setPassword(MD5.md5(user.getPassword()));
        } catch (Exception ex) {
            return false;
        }
        Date current = new Date();
        user.setResgiterdate(current);
        user.setModifydate(current);
        return userDao.insert(user) > 0;
    }

    public Boolean exist(String account) {
        return userDao.exist(account);
    }

    public User get(int id) {
        return userDao.selectByPrimaryKey(id);
    }

    public Boolean login(String account, String password) {

        try {
            password = MD5.md5(password);
        } catch (Exception ex) {
            return false;
        }
        return userDao.checkAccountAndPassword(account, password) > 0;
    }

    public Boolean delete(String account) {
        return userDao.deleteByAccount(account) > 0;
    }

    public Boolean delete(int id) {
        return userDao.deleteByPrimaryKey(id) > 0;
    }

    public ArrayList<User> get() {
        return userDao.all();
    }

    public Boolean update(User user) {
        user.setModifydate(new Date());
        return userDao.updateByPrimaryKey(user) > 0;
    }
}