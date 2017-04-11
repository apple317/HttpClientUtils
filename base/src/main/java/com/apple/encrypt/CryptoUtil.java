package com.apple.encrypt;


//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.util.Random;

/**
 * 加密解密共通
 * Created by ZHW on 2015/6/15.
 */
public class CryptoUtil {
    //private static Logger logger = LoggerFactory.getLogger(CryptoUtil.class);

    private static Md5PasswordEncoder encoder = new Md5PasswordEncoder();

    /**
     * 将字符串以md5加密
     *
     * @param value 被加密的字符串
     * @param key   密码
     * @return 加密后的字符串
     */
    public static String md5(String value, String key) {
        return encoder.encodePassword(value, key);
    }

    /**
     * 将字符串以md5加密
     *
     * @param value 被加密的字符串
     * @return 加密后的字符串
     */
    public static String md5(String value) {
        return encoder.encodePassword(value, null);
    }

    public static String getSecretKey() {
        return md5(String.valueOf(System.currentTimeMillis()));
    }

    /**
     * 生成验证码
     *
     * @param length 长度
     * @return 验证码
     */
    public static String getVerifyCode(int length) {
        Random random = new Random(System.currentTimeMillis());
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getSecretKey());
    }
}
