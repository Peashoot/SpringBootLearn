package com.peashoot.mybatis.mybatistest.util;

public enum DESPadding {
    NoPadding(1), PKCS5Padding(2), PKCS7Padding(3), ISO10126(4), ANSIX923(5);
    private final int value;

    private DESPadding(int value) {
        this.value = value;
    }

    public DESPadding valueOf(int value) {
        switch (value) {
        case 2:
            return PKCS5Padding;
        case 3:
            return PKCS7Padding;
        case 4:
            return ISO10126;
        case 5:
            return ANSIX923;
        default:
            return NoPadding;
        }
    }
}