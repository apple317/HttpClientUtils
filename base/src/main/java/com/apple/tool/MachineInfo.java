package com.apple.tool;



import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class MachineInfo {

	private static MachineInfo mInst = null;
	private static final String TAG = "MachineInfo";

	public String IMEI = "0000000000000000";
	public String IMSI = "";
	public String MANUFACTURER = "";
	public String MODEL = "";
	public String DEVICE_TYPE = "";
	public int SDK_VERSION = 3;
	public int DENSITY = 160;
	public String CONNECT_TYPE = "wifi";
	public String ANDROID_ID = "";

	private Context mContext;

	private MachineInfo(Context context){
		mContext = context;
		initInfo();
	}

	private void initInfo(){
//		IMEI = getIMEI();
//		IMSI = getIMSI();
//		ANDROID_ID = getANDROID_ID();
		getOtherInfo();
	}

	private void getOtherInfo() {
		try
		{
			Class<android.os.Build.VERSION> build_version_class = android.os.Build.VERSION.class;
	        java.lang.reflect.Field field = build_version_class.getField("SDK_INT");
	        SDK_VERSION = (Integer)field.get(new android.os.Build.VERSION());

	        Class<android.os.Build> build_class = android.os.Build.class;
	        java.lang.reflect.Field manu_field = build_class.getField("MANUFACTURER");
	        MANUFACTURER = (String) manu_field.get(new android.os.Build());
	        java.lang.reflect.Field field2 = build_class.getField("MODEL");
	        MODEL = (String) field2.get(new android.os.Build());
	        java.lang.reflect.Field device_field = build_class.getField("DEVICE");
	        DEVICE_TYPE = (String) device_field.get(new android.os.Build());
	        DENSITY = mContext.getResources().getDisplayMetrics().densityDpi;
		}catch(Exception e){
			Log.v(TAG, e.getMessage());
		}
	}

//	private String getIMEI(){
//		TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
//		String imei = tm.getDeviceId();
//		if(StrUtils.isStringEmpty(imei)){
//			imei = tm.getSimSerialNumber();
//		}
//		if(StrUtils.isStringEmpty(imei)){
//			imei = "0000000000000000";
//		}
//		return imei;
//	}
//
//	private String getIMSI(){
//		TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
//		String imsi = tm.getSubscriberId();
//		if(StrUtils.isStringEmpty(imsi)){
//			imsi = "";
//		}
//		return imsi;
//	}

	public static MachineInfo getInstance(Context context){
		if(null == mInst){
			mInst = new MachineInfo(context);
		}
		return mInst;
	}

//	private String getANDROID_ID(){
//	    return android.os.SystemProperties.get("ro.serialno");
//	}
//
    static public boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    static public long getAvailableExternalMemorySize() {
        if(externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            return -1;
        }
    }
    static public long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

}
