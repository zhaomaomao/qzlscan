package com.quanzhilian.qzlscan.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	
		public static String Join(List<String> lst, String separator) {
		   if(lst==null||lst.size()==0)
			   return null;
	       StringBuilder sb = new StringBuilder();
	       for(String i :lst){
	    	   sb.append(i + separator );   	   
	       }
	       String rtn = sb.toString();
	       return rtn.substring(0,rtn.length()-1);
	    }
		
		public static String GetDateNow(){
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		     String currentDateandTime = sdf.format(new Date());
		     return currentDateandTime;
		}
		
		public static String GetDayNow(){
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		     String currentDateandTime = sdf.format(new Date());
		     return currentDateandTime;
		}
		
		public static boolean IsNullOrEmpty(String str){
			return str==null || str.length()==0;
		}
		
		public static String calcutorAmount(double value){
			String str = String.valueOf(value);
			if(!TextUtils.isEmpty(str)){
				int len = str.substring(str.indexOf(".")).length();
				if(len > 4){
					DecimalFormat df = new DecimalFormat("#.##");
					return String.valueOf(df.format(value));
				}
			}			
			return str;						
		}
		
		public static boolean isNum(String str){
			return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
		}
		
		/**
		 * 判断字符串是否为null或""
		 * @param str
		 * @return
		 */
		public static boolean isEmpty(String str) {
			return (str == null) || (str.trim().length() == 0);
		}

		/** 现有电话号码的正则表达式 */
		private static String tel_reg = "^(1[0-9][0-9]|15[012356789]|18[012356789]|14[57])[0-9]{8}$";
		/***
		 * 判断是否是正确的电话号码
		 * @param str
		 * @return
		 */
		public static boolean isPhoneNum(String str) {
			Pattern pattern = Pattern.compile(tel_reg);
			Matcher matcher = pattern.matcher(str);
			return matcher.matches();
		}
		
		
		public static String getFromAssets(Context ctx, String fileName, String code){
	        try { 
	            InputStreamReader inputReader = new InputStreamReader(ctx.getResources().getAssets().open(fileName), code);
	            BufferedReader bufReader = new BufferedReader(inputReader);
	            String line="";
	            String Result="";
	            while((line = bufReader.readLine()) != null){
	            	Result += line;
	            }
	                
	            Log.i("result", Result);
	            return Result;
	        } catch (Exception e) {
	            e.printStackTrace(); 
	        }
	        return "";
	    }
}

