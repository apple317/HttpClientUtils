package com.apple.app;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;

import com.apple.tool.DeviceInfo;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;

public abstract class BaseApplication extends Application implements
		UncaughtExceptionHandler {

	public static String mDeviceId;
	private UncaughtExceptionHandler mDefaultHandler;
	public static String versionCode;
	public static String versionName;



	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	public BaseApplication() {
		DeviceInfo.init(this);
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}


	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		mDeviceId=getDeviceID(getApplicationContext());
		versionCode=getVerCode()+"";
		versionName=getVerName();
		super.onCreate();
	}


	public boolean isAppOnForeground() {
		// Returns a list of application processes that are running on the
		// device

		ActivityManager activityManager = (ActivityManager) getApplicationContext()
				.getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = getApplicationContext().getPackageName();

		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();

		if (appProcesses == null)
			return false;

		for (RunningAppProcessInfo appProcess : appProcesses) {
			// The name of the process that this object is associated with.
			if (appProcess.processName.equals(packageName)
					&& appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}

		return false;
	}



	public void onBackground() {

	}

	public void onForeground() {

	}

	/**
	 * 得到系统版本号
	 * @return
	 */
	public int getVerCode() {
		int verCode = -1;
		try {
			verCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return verCode;
	}

	/**
	 * 得到系统版本名称
	 * @return
	 */
	public String getVerName() {
		String verName = "";
		try {
			verName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return verName;
	}

	/**
	 * 控制应用退出操作
	 */
	@SuppressLint("NewApi")
	public void quit() {
		int version = android.os.Build.VERSION.SDK_INT;
		if (version <= 7) {
			System.out.println("   version  < 7");
			ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
			manager.restartPackage(getPackageName());
		} else {
			ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
			manager.killBackgroundProcesses(getPackageName());
		}
		int pid = android.os.Process.myPid();
		android.os.Process.killProcess(pid);
	}



	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		ex.printStackTrace();
		if (!treatErr(ex) && mDefaultHandler != null)
			mDefaultHandler.uncaughtException(thread, ex);
		else
			quit();
	}

	public boolean treatErr(Throwable ex) {
		return false;
	}

	/**
	 * 获取设备id
	 * @param context
	 * @return
	 */
	public static String getDeviceID(Context context) {
		if (mDeviceId == null) {
			mDeviceId = DeviceInfo.getDeviceInfo(context);
			if (mDeviceId == null || mDeviceId.length() < 2) {
				//mDeviceId = MySharedPreference.getString("clientid", null);
				if (mDeviceId == null) {
					mDeviceId = DeviceInfo.getUUID();
					//SharedPreferencesMgr.setString("clientid", mDeviceId);
				}
			}
		}
		return mDeviceId;
	}



}
