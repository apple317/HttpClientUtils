package com.apple.tool;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Environment;
import android.telephony.TelephonyManager;

import java.util.UUID;


public class DeviceInfo {

	private Context context;
	private float scale;
	private float screenWidth;
	private float screenHeight;

	private DeviceInfo(Context c) {
		context = c;
		instance = this;
		// scale = context.getResources().getDisplayMetrics().density;
	}

	public static void init(Context c) {
		new DeviceInfo(c);
	}

	public float getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(float screenWidth) {
		this.screenWidth = screenWidth;
	}

	public float getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(float screenHeight) {
		this.screenHeight = screenHeight;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public static DeviceInfo instance;

	/** �Ƿ��п��õ����� */
	public static boolean isNetWorkEnable(Context context) {
		// return isWifi(context)|| isMobile(context);
		return isNetAvailable(context);
	}

	/**
	 * @Description Checking if wifi is available
	 * @param context
	 *            Context
	 * @return true: wifi network is available, false: wifi network is
	 *         unavailable.
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (ni == null)
			return false;
		State st = ni.getState();
		return ni.getState() == State.CONNECTED;
		// boolean isWifiAvail = ni.isAvailable();
		// boolean isWifiConnect = ni.isConnected();
		// return isWifiAvail && isWifiConnect;
	}

	// dipת����
	public static int DipToPixels(Context context, int dip) {
		final float SCALE = context.getResources().getDisplayMetrics().density;
		float valueDips = dip;
		int valuePixels = (int) (valueDips * SCALE + 0.5f);
		return valuePixels;
	}

	/**
	 * @Description Checking if Mobile network is available
	 * @param context
	 *            Context
	 * @return true: mobile network is available, false: mobile network is
	 *         unavailable.
	 */
	public static boolean isMobile(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (ni == null)
			return false;
		return ni.getState() == State.CONNECTED;
		// boolean isMobileAvail = ni.isAvailable();
		// boolean isMobileConnect = ni.isConnected();
		// return isMobileAvail && isMobileConnect;
	}

	/**
	 * @Description Checking if Mobile network is available
	 * @param context
	 *            Context
	 * @return true: mobile network is available, false: mobile network is
	 *         unavailable.
	 */
	public static boolean isNetAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null)
			return false;
		return ni.getState() == State.CONNECTED;

	}

	/** �Ƿ���sd�� */
	public static boolean isSdcardExist() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean is3G(Context context) {
		TelephonyManager telephoneManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		final int type = telephoneManager.getNetworkType();
		switch (type) {

		// ��ͨ3g
		case TelephonyManager.NETWORK_TYPE_UMTS:
		case TelephonyManager.NETWORK_TYPE_HSDPA:
			// ����3g
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
			return true;
			// �ƶ�������ͨ2g
		case TelephonyManager.NETWORK_TYPE_GPRS:
		case TelephonyManager.NETWORK_TYPE_EDGE:
			// ����2g
		case TelephonyManager.NETWORK_TYPE_CDMA:
			return false;
		default:
			return false;
		}

	}
	public static int getSystemCode() {
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		return currentapiVersion;
	}
	/**
	 * ��ȡ��Ļ�ֱ���
	 * */
	public static int getResolution(Activity act) {
		int Width = 0;
		int Height = 0;
		Width = act.getWindowManager().getDefaultDisplay().getWidth();
		Height = act.getWindowManager().getDefaultDisplay().getHeight();

		return Width * Height;
	}

	public static boolean bChineseVersion() {
		return instance.context.getResources().getConfiguration().locale
				.toString().contains("zh");
	}

	private static String deviceId;

	public static String getAppVersion(Context context) {
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return packInfo != null ? packInfo.versionName : "";
	}

	public int getAppVersionCode() {
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return packInfo != null ? packInfo.versionCode : 0;
	}

	public static String getDeviceInfo(Context context) {
		if (deviceId != null)
			return deviceId;

		TelephonyManager tel = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		try {
			deviceId = tel.getDeviceId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (deviceId == null) {
			deviceId = instance.getAndroidId();
			// Log.d("client=", deviceId);
		}
		// System.out.println("devicId==="+"model=" + android.os.Build.MODEL +
		// ",release="
		// + android.os.Build.VERSION.RELEASE + ",sim=" + sim
		// + ",deviceId=" + dId);
		// deviceId = "model=" + android.os.Build.MODEL + ",release="
		// + android.os.Build.VERSION.RELEASE + ",sim=" + sim
		// + ",deviceId=" + dId);
		return deviceId;
	}

	private String getAndroidId() {
		return android.provider.Settings.Secure.getString(
				context.getContentResolver(),
				android.provider.Settings.Secure.ANDROID_ID);
	}

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}
}
