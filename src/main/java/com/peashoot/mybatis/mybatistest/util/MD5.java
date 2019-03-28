package com.peashoot.mybatis.mybatistest.util;

import org.springframework.util.DigestUtils;

public class MD5 {
    public static String md5(String text) {
        return md5(text, "");
    }

    public static String md5(String text, String key) {
        // 加密后的字符串
        String base = text + key;
        String encodeStr = DigestUtils.md5DigestAsHex(base.getBytes());
        System.out.println("MD5加密后的字符串为:encodeStr=" + encodeStr);
        return encodeStr;
    }

    public static boolean verify(String text, String key, String md5) {
        // 根据传入的密钥进行验证
        String md5Text = md5(text, key);
        if (md5Text.equalsIgnoreCase(md5)) {
            System.out.println("MD5验证通过");
            return true;
        }

        return false;
    }
}