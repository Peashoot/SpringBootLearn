package com.peashoot.mybatis.mybatistest.util;

public enum DESMode {
    ECB(1), CBC(2), CTR(3), OFB(4), CFB(5);

    private final int value;

    private DESMode(int value) {
        this.value = value;
    }

    public DESMode valueOf(int value) {
        switch (value) {
        case 2:
            return CBC;
        case 3:
            return CTR;
        case 4:
            return OFB;
        case 5:
            return CFB;
        default:
            return ECB;
        }
    }
}