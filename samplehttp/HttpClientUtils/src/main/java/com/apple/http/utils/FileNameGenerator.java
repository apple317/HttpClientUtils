package com.apple.http.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by android-dev on 15/5/19.
 */
public class FileNameGenerator {

    private static String cache_path;

    public static String generator(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String getMD5(String input) {
        String result = input;
        if (input != null) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5"); // or
                // "SHA-1"
                md.update(input.getBytes());
                BigInteger hash = new BigInteger(1, md.digest());
                result = hash.toString(16);
                while (result.length() < 32) {
                    result = "0" + result;
                }
            } catch (NoSuchAlgorithmException e) {
                return "";
            }
        }
        return result;
    }

    /**
     * ��ȡ�����ⲿ�Ļ���Ŀ¼
     *
     * @param context
     * @return
     */
    public static File getExternalCacheDir(Context context) {
        return new File(Environment.getExternalStorageDirectory().getPath());
    }

    public static File getExternalDir(Context context) {
        return new File(getCahePath(context));
    }

    public static final String getCahePath(Context ctx) {
        if (cache_path == null) {
            cache_path = Environment.MEDIA_MOUNTED.equals(Environment
                    .getExternalStorageState()) ? getExternalCacheDir(ctx)
                    .getPath() : ctx.getCacheDir().getPath();
        }
        return cache_path;
    }

    /**
     *
     * @param file
     */
    public static String getMIMEType(File file) {

        String type="*/*";
        String fName = file.getName();
        int dotIndex = fName.lastIndexOf(".");
        if(dotIndex < 0){
            return type;
        }
        String end=fName.substring(dotIndex,fName.length()).toLowerCase();
        if(end=="")return type;
        for(int i=0;i<MIME_MapTable.length;i++){
            if(end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }




    private static  String[][] MIME_MapTable={
            {".bmp",    "image/bmp"},
            {".gif",    "image/gif"},
            {".jpeg",   "image/jpeg"},
            {".jpg",    "image/jpeg"},
            {".png",    "image/png"},
    };
}
