package com.peashoot.mybatis.mybatistest.entity;

import java.net.URLDecoder;
import java.util.HashMap;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSON;
import com.peashoot.mybatis.mybatistest.util.DES3;
import com.peashoot.mybatis.mybatistest.util.DESMode;
import com.peashoot.mybatis.mybatistest.util.DESPadding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
public class CookieMap extends HashMap<String, String> {
    private static final long serialVersionUID = 1L;
    // private volatile static CookieMap instance;

    // public static CookieMap getInstance() {
    //     if (instance == null) {
    //         synchronized (CookieMap.class) {
    //             if (instance == null) {
    //                 instance = new CookieMap();
    //             }
    //         }
    //     }
    //     return instance;
    // }

    @Value("${com.peashoot.encrypt.key}")
    private String encryptKey;
    @Value("${com.peashoot.encrypt.iv}")
    private String encryptIv;
    @Value("${com.peashoot.cookie.name}")
    private String cookieName;
    @Value("${com.peashoot.cookie.lifetime}")
    private int cookieLifetime;

    @Override
    public String toString() {
        String unEncrypt = JSON.toJSONString(this);
        try {
            return DES3.encode(encryptKey, encryptIv, unEncrypt, DESMode.ECB, DESPadding.PKCS7Padding);
        } catch (Exception ex) {
            return "";
        }
    }

    public String getValue(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    try {
                        String encrypted = cookie.getValue();
                        encrypted = URLDecoder.decode(encrypted, "utf-8");
                        String unEncrypt = DES3.decode(encryptKey, encryptIv, encrypted, DESMode.ECB,
                                DESPadding.PKCS7Padding);
                        CookieMap map = JSON.parseObject(unEncrypt, CookieMap.class);
                        return map.get(key);
                    } catch (Exception ex) {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    public String refreshString(String key, String value) {
        put(key, value);
        return toString();
    }

    public Cookie createCookie(String key, String value) {
        return createCookie(key, value, cookieLifetime, "/");
    }

    public Cookie createCookie(String key, String value, int maxAge) {
        return createCookie(key, value, maxAge, "/");
    }

    public Cookie createCookie(String key, String value, int maxAge, String path) {
        String cookieJson = refreshString(key, value);
        Cookie cookie = new Cookie(cookieName, cookieJson);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        return cookie;
    }
}