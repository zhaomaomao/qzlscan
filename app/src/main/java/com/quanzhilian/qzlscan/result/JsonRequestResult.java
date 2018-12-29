package com.quanzhilian.qzlscan.result;

import com.alibaba.fastjson.JSONObject;
import com.quanzhilian.qzlscan.models.sqlmodel.RpositoryBillModel;

import java.util.List;

/**
 * Created by hushouqi on 16/3/15.
 */
public class JsonRequestResult {


    public JsonRequestResult() {

    }

    public static JsonRequestResult toJsonRequestResult(String jsonResultStr) {
        try {
            JsonRequestResult jsonRequestResult = JSONObject.parseObject(jsonResultStr, JsonRequestResult.class);
            return jsonRequestResult;
        } catch (Exception e) {
            throw e;
        }
    }

    private String msg;

    private int code;

    private Object obj;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int resultCode) {
        this.code = resultCode;
    }

    public Object getObj() {
        return obj;
    }


    public void setObj(Object jsonObject) {
        this.obj = jsonObject;
    }

    public <T> T getResultObjBean(Class<T> tClass) {
        if (this.obj != null)
            //return JSON.toJavaObject((JSON) this.resultObj, tClass);
            return JSONObject.parseObject(JSONObject.toJSONString(this.obj), tClass);
        else
            return null;
    }

    public <T> List<T> getResultBeanList(Class<T> clazz) {
        if (this.obj != null) {
            //return JSON.parseArray((String) this.resultObj, clazz);
            return JSONObject.parseArray(JSONObject.toJSONString(this.obj), clazz);
        } else
            return null;
    }

    public <T> T getResultObjBean(Class<T> tClass, String resultKey) {
        if (this.obj != null) {
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(this.obj)).getJSONObject(resultKey);
            return JSONObject.parseObject(object.toJSONString(), tClass);
        } else
            return null;
    }

    public <T> List<T> getResultBeanList(Class<T> clazz, String resultKey) {
        if (this.obj != null) {
            JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(this.obj));
            String jsonObject = object.getString(resultKey);
            if(object!=null) {
                return JSONObject.parseArray(jsonObject, clazz);
            }else return null;
        } else
            return null;
    }
}
