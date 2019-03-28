package com.peashoot.mybatis.mybatistest.control;

import javax.annotation.Resource;

import com.peashoot.mybatis.mybatistest.entity.User;
import com.peashoot.mybatis.mybatistest.service.IUserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "user")
@Api("userController相关的api")
public class UserController {
    @Resource
    private IUserService userService;

    @ApiOperation(value = "Hello", notes = "显示hello")
    @RequestMapping(path = "hello", method = { RequestMethod.GET, RequestMethod.POST })
    public String hello() {
        return "hello";
    }

    @ApiOperation(value = "添加", notes = "添加用户信息")
    @ApiImplicitParam(name = "user", value = "用户信息", paramType = "body", required = true, dataType = "com.peashoot.mybatis.mybatistest.entity.User")
    @PostMapping(path = "insert")
    public Boolean insert(@RequestBody User user) {
        return userService.insert(user);
    }

    @ApiOperation(value = "登录", notes = "用户输入账号密码进行登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "用户账号", paramType = "body", required = true, dataType = "java.lang.String"),
            @ApiImplicitParam(name = "password", value = "用户密码", paramType = "body", required = true, dataType = "java.lang.String") })
    @RequestMapping(path = "login", method = { RequestMethod.GET, RequestMethod.POST })
    public Boolean login(@RequestParam("account") String account, @RequestParam("password") String password) {
        return userService.login(account, password);
    }

    @ApiOperation(value = "获取", notes = "根据ID获取用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", paramType = "path", required = true, dataType = "java.lang.Integer")
    @RequestMapping(value = "get/{id}", method = { RequestMethod.GET, RequestMethod.POST })
    public User requestMethodName(@PathVariable int id) {
        return userService.get(id);
    }

    @ApiOperation(value = "删除", notes = "根据用户名删除用户信息")
    @ApiImplicitParam(name = "account", value = "用户账号", paramType = "param", required = true, dataType = "java.lang.String")
    @RequestMapping(value = "delete", method = { RequestMethod.GET, RequestMethod.POST })
    public Boolean delete(@RequestParam("account") String account) {
        return userService.delete(account);
    }

}