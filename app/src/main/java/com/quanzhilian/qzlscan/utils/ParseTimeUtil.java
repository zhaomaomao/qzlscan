package com.quanzhilian.qzlscan.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseTimeUtil {
	public static String format(long ms) {
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		   long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		   String strDay = day < 10 ? "0" + day : "" + day;
		String strHour = hour < 10 ? "0" + hour : "" + hour;
		String strMinute = minute < 10 ? "0" + minute : "" + minute;
		String strSecond = second < 10 ? "0" + second : "" + second;
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
		return strMinute + ":" + strSecond ;

		}

	/**
	 * 根据时间戳获取时间
	 * @param timestamp
	 * @param format
	 * @return
	 */
	public static String getDayToStamp(long timestamp , String format){
		String f = "yyyy-MM-dd";
		if(format != null && !"".equals(format.trim())){
			f = format;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(f);
		String date = sdf.format(new Date(timestamp));

		return date;
	}

	/**
	 * 根据时间戳获取时间字符串
	 * @param stamp
	 * @return
	 */
	public static String getDateForStamp(long stamp){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date(stamp));
	}

	/**
	 * get current time in milliseconds
	 *
	 * @return
	 */
	public static long getCurrentTimeInLong() {
		return System.currentTimeMillis();
	}


}
