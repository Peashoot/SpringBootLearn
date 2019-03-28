package com.peashoot.mybatis.mybatistest.entity;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorJson {
    public ErrorJson(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        timestamp = new Date().getTime();
        message = ex == null ? "No message available" : ex.getMessage();
        path = request.getPathInfo();
        status = response.getStatus();
    }
    private long timestamp;
    private String path;
    private String message;
    private int status;
    private String error;
}