package com.apple.tool;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.widget.EditText;


/**
 * Created by zhangliang on 2016/11/1.
 */
public class Utility {
    public static void getToast(String msg, Context c) {
        Toast.show(c, msg, 1);
    }

    public static Dialog getDialog(Context context) {
        return new LoadingDialog(context);
    }

    public static String getString(EditText s){
        return s.getText().toString().trim();
    }

    public static int getAppVersionId (Context context){
        int code=0;
        try {
            code= context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            return  code;
        }catch (Exception e){
            e.printStackTrace();
        }
       return code;
    }
    public static String decode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5) && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr.charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else
                    retBuf.append(unicodeStr.charAt(i));
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static String channelName(Context a){
        String msg="sdm";
        try {
            ApplicationInfo appInfo = a.getPackageManager()
                    .getApplicationInfo(a.getPackageName(),
                            PackageManager.GET_META_DATA);
             msg = appInfo.metaData.getString("UMENG_CHANNEL", "");
        }catch (Exception e){
            e.printStackTrace();
        }

        return msg;


    }
    public static String Stringinsert(String a, String s, int t) {
        return a.substring(0, t) + s + a.substring(t);
    }
}
