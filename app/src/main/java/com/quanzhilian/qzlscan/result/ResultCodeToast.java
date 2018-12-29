package com.quanzhilian.qzlscan.result;

import java.util.HashMap;
import java.util.Map;

public class ResultCodeToast {

    private static Map<String,String> resultCodeToastMap=new HashMap<String,String>();

    private  static  ResultCodeToast resultCodeToast;

    private ResultCodeToast()
    {
        /*用户登录验证提示*/
        resultCodeToastMap.put("-2","当前账号登录状态已过期，请重新登录");
    }

    public static ResultCodeToast getInst() {
        if (resultCodeToast == null) {
            synchronized (ResultCodeToast.class) {
                resultCodeToast = new ResultCodeToast();
            }
        }
        return resultCodeToast;
    }


    /*获取提示文本*/
    public String getToastText(String code)
    {
        if(resultCodeToastMap.containsKey(code))
            return resultCodeToastMap.get(code);
        else
        {
            return "undefine toast message";
        }

    }







}
