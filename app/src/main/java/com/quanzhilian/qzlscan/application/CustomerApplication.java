package com.quanzhilian.qzlscan.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.quanzhilian.qzlscan.R;
import com.quanzhilian.qzlscan.scan.ClientConfig;
import com.quanzhilian.qzlscan.utils.StringUtils;

public class CustomerApplication extends Application {
	public static Context applicationContext;
	private static CustomerApplication instance;
	public static CountDownTimer timer;
	public int scanType = 1;

	public long isUpdateVersionTime = 0;
	@Override
	public void onCreate() {
		super.onCreate();
		applicationContext = this;
		instance = this;
	}

	public static CustomerApplication getInstance() {
		return instance;
	}

	/**
	 * 返回当前程序版本名
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}

	// 初次打开
	public static void setisFirst(boolean is, Context context) {
		SharedPreferences mSpSetting = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = mSpSetting.edit();
		editor.putBoolean(getAppVersionName(context) + "ISFIRST", is).commit();
	}

	// 判断是不是第一次打开
	public static boolean isFirst(Context context) {
		SharedPreferences mSpSetting = PreferenceManager.getDefaultSharedPreferences(context);
		return mSpSetting.getBoolean(getAppVersionName(context) + "ISFIRST", true);
	}
}
