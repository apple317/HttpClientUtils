package com.apple.encrypt;



import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES加密解密共通
 */
public class DesUtil {

    private final static String DES = "DES";
    private final static String DEFAULT_KEY = "s17ucAicatc0path";


    /**
     * Description 根据键值进行加密
     *
     * @param data 明文
     * @return 密文
     */
    public static String encrypt(String data) {
        try {
            return encrypt(data, DEFAULT_KEY);
        }catch (Exception e) {
            e.printStackTrace();
            return data;
        }


    }

    /**
     * Description 根据键值进行加密
     *
     * @param data 明文
     * @return 密文
     */
    public static String encrypt2(String data,String key) {
        try {
            return encrypt(data, key);
        }catch (Exception e) {
            e.printStackTrace();
            return data;
        }


    }

    /**
     * Description 根据键值进行加密
     *
     * @param data 明文
     * @return 密文
     * @throws Exception
     */
    public static String encryptEx(String data) throws Exception{

            return encrypt(data, DEFAULT_KEY);
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data 密文
     * @return 明文
     */
    public static String decryptKey(String key,String data)  {
        try {
            //   LogUtil.i("zhang",decrypt(data, key)+"解密成功。。。。。。");
            return decrypt(data, key);
        }catch (Exception e) {
            e.printStackTrace();
            //    LogUtil.i("zhang",data+"解密失败。。。。。。");
            return data;
        }

    }


    /**
     * Description 根据键值进行解密
     *
     * @param data 密文
     * @return 明文
     */
    public static String decrypt(String data)  {
        try {
            return decrypt(data, DEFAULT_KEY);
        }catch (Exception e) {
            e.printStackTrace();
            return data;
        }

    }

    /**
     * Description 根据键值进行解密
     *
     * @param data 密文
     * @return 明文
     */
    public static String decrypt2(String data,String key)  {
        try {
            return decrypt(data, key);
        }catch (Exception e) {
            e.printStackTrace();
            return data;
        }

    }

    /**
     * Description 根据键值进行解密
     *
     * @param data 密文
     * @return 明文
     * @throws IOException
     * @throws Exception
     */
    public static String decryptEx(String data) throws Exception{

            return decrypt(data, DEFAULT_KEY);


    }


    /**
     * Description 根据键值进行解密
     *
     * @param data 密文
     * @return 明文
     * @throws IOException
     * @throws Exception
     */
    public static String decryptEx(String data,String key) throws Exception{

        return decrypt(data, key);


    }



    /**
     * Description 根据键值进行加密
     *
     * @param data 明文
     * @param key  加密键byte数组
     * @return 密文
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes("UTF-8"), key.getBytes());
        return new BASE64Encoder().encode(bt);
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data 密文
     * @param key  加密键byte数组
     * @return 明文
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws
            Exception {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf, key.getBytes());
        return new String(bt, "UTF-8");
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data 明文
     * @param key  加密键byte数组
     * @return 密文
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }


    /**
     * Description 根据键值进行解密
     *
     * @param data 密文
     * @param key  加密键byte数组
     * @return 明文
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }

    public static void main(String[] args) {
        System.out.println(encrypt2("227965","dDc7bdON3ej3jtns"));

    }
}
