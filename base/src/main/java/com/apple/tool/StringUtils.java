package com.apple.tool;

import android.text.Html;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;


/**
 * @author hao.xiong
 * @version 1.0.1
 * @since 2012-03-22
 */
public class StringUtils {
    private static final int CACHE_SIZE = 4096;

    /**
     * 将s中字符用delimiter分割连接起来
     *
     * @param delimiter 分隔符
     * @param segments  被连接的数据
     * @return 返回连接号的字符串
     * @see StringUtils#join(String, Object[])
     */
    public static String join(String delimiter, Collection<?> segments) {
        StringBuilder stringBuilder = new StringBuilder();
        if (segments != null) {
            appendCollectionObjectToStringBuilder(stringBuilder, delimiter, segments);
        }
        String outString = stringBuilder.toString();
        if (outString.endsWith(delimiter)) {
            return outString.substring(0, outString.length() - delimiter.length());
        }
        return outString;
    }

    /**
     * 将对象链接成一个字符串，使用delimiter传入的字符串分割，
     * <p><b>注意:</b>如果前一个片段为字符串且以delimiter结束或者为空(null获取为"")，将不会重复添加此字符串</p>
     * <p>字符串末尾不会自动添加delimiter字符串</p>
     * <p>如果没有传入参数，返回一个<b>空字符串</b></p>
     *
     * @param delimiter 分割字符串
     * @param segments  所有传入的部分
     * @return 连接完毕后的字符串
     */
    public static String join(String delimiter, Object... segments) {
        StringBuilder stringBuilder = new StringBuilder();
        if (segments != null) {
            int size = segments.length;
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    appendObjectToStringBuilder(stringBuilder, delimiter, segments[i]);
                }
            }
        }
        String outString = stringBuilder.toString();
        if (outString.endsWith(delimiter)) {
            return outString.substring(0, outString.length() - delimiter.length());
        }
        return outString;
    }

    /**
     * html to String
     * @param str 原字符串
     * @return 转换后的字符串
     */
    public static String html2String(String str) {
        return StringUtils.isEmpty(str) ? str : Html.fromHtml(str).toString();
    }

    private static void appendArrayObjectToStringBuilder(StringBuilder stringBuilder, String delimiter, Object array) {
        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            appendObjectToStringBuilder(stringBuilder, delimiter, Array.get(array, i));
        }
    }

    private static void appendCollectionObjectToStringBuilder(StringBuilder stringBuilder, String delimiter, Collection<?> collection) {
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            appendObjectToStringBuilder(stringBuilder, delimiter, iterator.next());
        }
    }

    private static void appendObjectToStringBuilder(StringBuilder stringBuilder, String delimiter, Object object) {
        if (object == null) {
            return;
        }
        if (object.getClass().isArray()) {
            appendArrayObjectToStringBuilder(stringBuilder, delimiter, object);
        } else if (object instanceof Collection) {
            appendCollectionObjectToStringBuilder(stringBuilder, delimiter, (Collection)object);
        } else {
            String objectString = object.toString();
            stringBuilder.append(objectString);
            if (!isEmpty(objectString) && !objectString.endsWith(delimiter)) {
                stringBuilder.append(delimiter);
            }
        }
    }

    /**
     * 测试传入的字符串是否为空
     *
     * @param string 需要测试的字符串
     * @return 如果字符串为空（包括不为空但其中为空白字符串的情况）返回true，否则返回false
     */
    public static boolean isEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }

    /**
     * 传入的字符串是否相等
     *
     * @param a a字符串
     * @param b b字符串
     * @return 如果字符串相等（值比较）返回true，否则返回false
     */
    public static boolean equal(String a, String b) {
        return a == b || (a != null && b != null && a.length() == b.length() && a.equals(b));
    }

    /**
     * 将字符串用分隔符分割为long数组
     * <p><b>只支持10进制的数值转换</b></p>
     * <p><b>如果格式错误，将抛出NumberFormatException</b></p>
     *
     * @param string    字符串
     * @param delimiter 分隔符
     * @return 分割后的long数组.
     */
    public static long[] splitToLongArray(String string, String delimiter) {
        List<String> stringList = splitToStringList(string, delimiter);

        long[] longArray = new long[stringList.size()];
        int i = 0;
        for (String str : stringList) {
            longArray[i++] = Long.parseLong(str);
        }
        return longArray;
    }

/**
     * 将字符串用分隔符分割为Long列表
     * <p><b>只支持10进制的数值转换</b></p>
     * <p><b>如果格式错误，将抛出NumberFormatException</b></p>
     *
     * @param string    字符串
     * @param delimiter 分隔符
     * @return 分割后的Long列表.
     */
    public static List<Long> splitToLongList(String string, String delimiter) {
        List<String> stringList = splitToStringList(string, delimiter);

        List<Long> longList = new ArrayList<Long>(stringList.size());
        for (String str : stringList) {
            longList.add(Long.parseLong(str));
        }
        return longList;
    }

    /**
     * 将字符串用分隔符分割为字符串数组
     *
     * @param string    字符串
     * @param delimiter 分隔符
     * @return 分割后的字符串数组.
     */
    public static String[] splitToStringArray(String string, String delimiter) {
        List<String> stringList = splitToStringList(string, delimiter);
        return stringList.toArray(new String[stringList.size()]);
    }

    /**
     * 将字符串用分隔符分割为字符串列表
     *
     * @param string    字符串
     * @param delimiter 分隔符
     * @return 分割后的字符串数组.
     */
    public static List<String> splitToStringList(String string, String delimiter) {
        List<String> stringList = new ArrayList<String>();
        if (!isEmpty(string)) {
            int length = string.length();
            int pos = 0;

            do {
                int end = string.indexOf(delimiter, pos);
                if (end == -1) {
                    end = length;
                }
                stringList.add(string.substring(pos, end));
                pos = end + 1; // skip the delimiter
            } while (pos < length);
        }
        return stringList;
    }

    /**
     * InputSteam 转换到 String，会把输入流关闭
     *
     * @param inputStream 输入流
     * @return String 如果有异常则返回null
     */
    public static String stringFromInputStream(InputStream inputStream) {
        try {
            byte[] readBuffer = new byte[CACHE_SIZE];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int readLen = inputStream.read(readBuffer, 0, CACHE_SIZE);
                if (readLen <= 0) {
                    break;
                }

                byteArrayOutputStream.write(readBuffer, 0, readLen);
            }

            return byteArrayOutputStream.toString("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 测试是否为有效的注册电子邮件格式
     *
     * @param string 内容
     * @return true if yes
     */
    public static boolean isEmailFormat(String string) {
        if (StringUtils.isEmpty(string)) {
            return false;
        }
        String emailFormat = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        return Pattern.matches(emailFormat, string);
    }

    /**
     * 测试是否为有效的手机号码
     * @param phoneNum phoneNum
     * @return true if yes
     */
    public static boolean isMobileNumFormat(String phoneNum) {
        if (StringUtils.isEmpty(phoneNum)) {
            return false;
        }
        final int phoneNumLength = 11;
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(phoneNum).matches() && phoneNum.length() == phoneNumLength;
    }

    /**
     * 测试是否为有效的登录电子邮件格式
     *
     * @param string 内容
     * @return true if yes
     */
    public static boolean isLoginEmailFormat(String string) {
        if (StringUtils.isEmpty(string)) {
            return false;
        }
        String emailFormat = "^\\s*\\w+(?:\\.?[\\w-]+\\.?)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        return Pattern.matches(emailFormat, string);
    }

    /**
     * 是否在长度范围之类
     * @param string 内容
     * @param begin 最小长度（inclusive）
     * @param end 最大长度（inclusive）
     * @return 字符串长度在begin和end之内返回true，否则返回false。<p><b>输入字符串为null时，返回false</b></p>
     */
    public static boolean lengthInRange(String string, int begin, int end) {
        return string != null && string.length() >= begin && string.length() <= end;
    }

    /**
     * 去除文件名中的无效字符
     * @param srcStr srcStr
     * @return 去除文件名后的字符
     */
    public static String validateFilePath(String srcStr) {
        return StringUtils.isEmpty(srcStr) ? srcStr : srcStr.replaceAll("[\\\\/:\"*?<>|]+", "_");
    }

}


