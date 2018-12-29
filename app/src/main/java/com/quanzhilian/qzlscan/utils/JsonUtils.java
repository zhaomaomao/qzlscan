package com.quanzhilian.qzlscan.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * Created by hushouqi on 16/3/22.
 */
public class JsonUtils {

    public  static Map<String,String> beanToMap(Object object)
    {
        try
        {
            if(object!=null)
            {
                String jsonString= JSON.toJSONString(object);
                if(!TextUtils.isEmpty(jsonString)){

                    return  JSON.toJavaObject(JSON.parseObject(jsonString), Map.class);

                }
            }
            return null;

        }catch (Exception ex)
        {
            return null;

        }

    }
}
