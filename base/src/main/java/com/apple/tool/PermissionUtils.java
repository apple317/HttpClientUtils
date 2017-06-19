package com.apple.tool;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by terence.wang on 2017/2/20.
 * 6.0系统 权限检查
 */

public class PermissionUtils {

    //手机状态权限
    public static final int REQUEST_CODE_READ_PHONE_STATE = 0X01;

    //照相机权限
    public static final int REQUEST_CODE_CAMERA_STATE =0X02;

    //录音权限
    public static final int REQUEST_CODE_RECORD_AUDIO = 0X03;

    // 修改录音设置权限
    public static final int REQUEST_CODE_MODIFY_AUDIO_SETTINGS = 0X04;

    //写入文件权限
    public static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 0X05;
    //文档操作权限
    public static final int REQUEST_CODE_MANAGE_DOCUMENTS = 0X06;

    /**
     * 检查是否授予查看手机状态权限
     * @param context
     * @return
     */
    public static boolean checkPhoneStatePermission(Context context){
        if (!PermissionUtils.checkoutPermission((Activity) context, Manifest.permission.READ_PHONE_STATE, PermissionUtils.REQUEST_CODE_READ_PHONE_STATE)) {
            return false;
        }
        return true;
    }
    /**
     * 检查是否授予文件读写权限
     * @param context
     * @return
     */
    public static boolean checkExternalStoragePermission(Context context){
        if (!PermissionUtils.checkoutPermission((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE, PermissionUtils.REQUEST_CODE_WRITE_EXTERNAL_STORAGE)) {
            return false;
        }
        return true;
    }


    /**
     * 检查Agora的相关权限是否已授权
     * @param context
     * @return
     */
    public static boolean checkCameraPermission(Context context){
        if (!checkoutPermission((Activity) context, Manifest.permission.CAMERA, REQUEST_CODE_CAMERA_STATE)) {
            return false;
        }

        if (!checkoutPermission((Activity) context, Manifest.permission.RECORD_AUDIO, REQUEST_CODE_RECORD_AUDIO)) {
            return false;
        }
        return true;
    }

    /**
     * 检测是否有权限
     * @param context
     * @param permission
     * @param requestCode
     * @return
     */
    public static boolean checkoutPermission(Activity context, String permission, int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            int cp = ContextCompat.checkSelfPermission(context, permission);
            if (cp != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context, new String[]{permission}, requestCode);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     *  检测是否有获取相册和照相机及存储权限
     * @param context
     * @return
     */
    public static boolean checkPicturPermissions(Context context){
        //android.permission.MANAGE_DOCUMENTS
//        if (!checkoutPermission((Activity) context, Manifest.permission.MANAGE_DOCUMENTS, REQUEST_CODE_MANAGE_DOCUMENTS)) {
//            return false;
//        }
        if (!checkoutPermission((Activity) context, Manifest.permission.CAMERA, REQUEST_CODE_CAMERA_STATE)) {
            return false;
        }

        if (!checkoutPermission((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_CODE_WRITE_EXTERNAL_STORAGE)) {
            return false;
        }

        return true;
    }
}
