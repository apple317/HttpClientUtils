package com.apple.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by HUANG on 14-7-4.
 * 常用验证方式
 */
public class Validation {
    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobilePhoneNumber(String str) {
        Pattern p;
        Matcher m;
        boolean b;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static boolean isNonEmptyString(String str) {
        return !"".equals(str.trim());
    }

    public static boolean isNonNull(Object obj) {
        return !(obj == null);
    }

    public static boolean isPassword(String str) {
        return str.length() >= 6;
    }

    public static boolean isVerificationCode(String str) {
        Pattern p;
        Matcher m;
        boolean b;
        p = Pattern.compile("^[0-9]{6}$"); //校验验证码
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
}
