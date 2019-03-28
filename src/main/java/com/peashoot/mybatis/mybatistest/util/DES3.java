package com.peashoot.mybatis.mybatistest.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class DES3 {
    /***
     * DES3 加密
     * 
     * @param key     加密密钥
     * @param iv      填充向量
     * @param data    加密字符串
     * @param mode    加密模式
     * @param padding 向量填充模式
     * @return 加密后字符串
     */
    public static String encode(String key, String iv, String data, DESMode mode, DESPadding padding) {
        try {
            byte[] bKey = build3DesKey(key);
            byte[] bIv = iv.getBytes();
            byte[] bData = data.getBytes("UTF-8");
            byte[] encoded = encode(bKey, bIv, bData, mode, padding);
            return Base64.encodeBase64String(encoded);
        } catch (Exception ex) {
            return "";
        }
    }

    /***
     * DES3 加密
     * 
     * @param key     加密密钥
     * @param iv      填充向量
     * @param data    加密byte数组
     * @param mode    加密模式
     * @param padding 向量填充模式
     * @return 加密后byte数组
     */
    public static byte[] encode(byte[] key, byte[] keyiv, byte[] data, DESMode mode, DESPadding padding) {
        try {
            Cipher cipher = getCipher(key, keyiv, mode, padding, Cipher.ENCRYPT_MODE);
            byte[] bOut = cipher.doFinal(data);
            return bOut;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * DES3 解密
     * 
     * @param key     解密秘钥
     * @param iv      填充向量
     * @param data    待解密字符串
     * @param mode    加密模式
     * @param padding 向量填充方式
     * @return 解密后字符串
     */
    public static String decode(String key, String iv, String data, DESMode mode, DESPadding padding) {
        try {
            byte[] bKey = build3DesKey(key);
            byte[] bIv = iv.getBytes();
            byte[] bData = Base64.decodeBase64(data);
            byte[] encoded = decode(bKey, bIv, bData, mode, padding);
            return new String(encoded, "UTF-8");
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * DES3 解密
     * 
     * @param key     解密秘钥
     * @param iv      填充向量
     * @param data    待解密byte数组
     * @param mode    加密模式
     * @param padding 向量填充方式
     * @return 解密后byte数组
     */
    public static byte[] decode(byte[] key, byte[] keyiv, byte[] data, DESMode mode, DESPadding padding) {
        try {
            Cipher cipher = getCipher(key, keyiv, mode, padding, Cipher.DECRYPT_MODE);
            byte[] bOut = cipher.doFinal(data);
            return bOut;
        } catch (Exception ex) {
            return null;
        }
    }

    /***
     * 获取加密方法
     * @param key
     * @param keyiv
     * @param mode
     * @param padding
     * @param encodeOrDecode
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidAlgorithmParameterException
     */
    private static Cipher getCipher(byte[] key, byte[] keyiv, DESMode mode, DESPadding padding, int encodeOrDecode)
            throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException,
            BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        Key deskey = keyfactory.generateSecret(spec);
        Security.addProvider(new BouncyCastleProvider());// java不自带支持PKCS7Padding,需要添加这句
        Cipher cipher = Cipher.getInstance("desede/" + mode.toString() + "/" + padding.toString());
        if (mode == DESMode.ECB) {
            cipher.init(encodeOrDecode, deskey);
        } else {
            IvParameterSpec ips = new IvParameterSpec(keyiv);
            cipher.init(encodeOrDecode, deskey, ips);
        }
        return cipher;
    }

    // 构建3DES密钥
    public static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException {
        byte[] key = new byte[24]; // 声明一个24位的字节数组，默认里面都是0
        byte[] temp = keyStr.getBytes("UTF-8"); // 将字符串转成字节数组
        /*
         * 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
         */
        if (key.length > temp.length) {
            // 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            // 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    }
}