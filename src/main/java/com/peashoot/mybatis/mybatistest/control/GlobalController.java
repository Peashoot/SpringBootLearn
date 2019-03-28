package com.peashoot.mybatis.mybatistest.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.peashoot.mybatis.mybatistest.entity.CookieMap;
import com.peashoot.mybatis.mybatistest.entity.ErrorJson;
import com.peashoot.mybatis.mybatistest.service.IUserService;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalController {
    @Resource
    private IUserService userService;
    @Resource
    private CookieMap cookieMap;

    @ModelAttribute(name = "login_account")
    public String loginAccount(HttpServletRequest request) {
        String sessionId = cookieMap.getValue(request, "login_session_id");
        if (sessionId != null && sessionId != "") {
            return sessionId.replaceFirst("login_account_", "");
        }
        return null;
    }

    @ResponseBody
    @ExceptionHandler
    public String error(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        return JSON.toJSONString(new ErrorJson(request, response, ex));
    }
}