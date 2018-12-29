package com.quanzhilian.qzlscan.utils;

/**
 * Created by hushouqi on 16/3/15.
 */
public class UrlUtils {

    //获取完整请求地址
    public static String getFullUrl(String toUrl)
    {
        return  UrlConstant.url_app_service_server+toUrl;
    }


}
