/**
 * 
 */
package com.quanzhilian.qzlscan.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetWorkUtils {

	/**
	 * 判断是否有网络，不返回网络类型 false 不可用
	 * @param context
	 * @return
	 */
	public static boolean isNetWork(Context context) {


		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null) { 
			boolean isavailable = info.isAvailable();
			boolean isconnected = info.isConnected();
			if (isavailable && isconnected) {	

				return true;
			}else{

				return false;
			}
		}
		return false;
	}
	
	/**
	 *
	 * @param context
	 * @return
	 */
	public static String getNetWorkType(Context context) {


		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();  // ��ü����������Ϣ
		if (info != null) { 
			boolean isavailable = info.isAvailable(); // ��ǰ�����Ƿ���Ч
			boolean isconnected = info.isConnected(); // ��ǰ�����Ƿ�������
			if (isavailable && isconnected) {	

				String netType = info.getTypeName();  // ��ǰ��������� for example "WIFI" or "MOBILE".
				return netType;
			}else{

				return null;
			}
		}
		return null;
	}

	/**
	 *
	 * @param context
	 * @return
	 */
	private static boolean isMOBBLEConnectivity(Context context) {
		//
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);


		NetworkInfo networkinfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkinfo != null) {
			return networkinfo.isConnected();
		}
		return false;
	}

	/**
	 *
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isWIFIConnectivity(Context context) {

		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);


		NetworkInfo networkinfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkinfo != null) {
			return networkinfo.isConnected();
		}
		return false;
	}

}
