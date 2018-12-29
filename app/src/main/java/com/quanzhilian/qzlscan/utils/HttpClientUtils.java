/**
 *
 */
package com.quanzhilian.qzlscan.utils;


import android.preference.PreferenceActivity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.quanzhilian.qzlscan.result.JsonRequestResult;
import com.quanzhilian.qzlscan.result.ResultCodeConstant;

import java.io.File;
import java.util.Map;

/**
 * @author
 */
public class HttpClientUtils {
    private OnGetResponseData onGetResponseData;

    public void setOnGetData(OnGetResponseData onGetResponseData) {
        this.onGetResponseData = onGetResponseData;
    }

    public void postRequest(String url, Map<String, String> datas)
            throws Exception {
        LogUtils.e("\r\n请求的url地址为："+url);
        LogUtils.e("\r\n请求的参数为："+ JSONObject.toJSONString(datas));
        RequestParams params = null;
        params = new RequestParams();
        addNecessaryParas(params);
        if (datas != null) {
            for (String key : datas.keySet()) {
                params.addQueryStringParameter(key, datas.get(key));
            }
        }else{
            params.addQueryStringParameter("app", "scan");
        }

        HttpUtils http = new HttpUtils();
        http.configTimeout(300000);
        LogUtils.d(url);
        http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {


            @Override
            public void onStart() {
                LogUtils.d("conn...");
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    String result = responseInfo.result;
                    LogUtils.e("\r\n请求的结果为："+result);
                    //JsonRequestResult jsonRequestResult = JsonRequestResult.toJsonRequestResult(result);
                    if (onGetResponseData != null)
                        onGetResponseData.onGetData(result);
                } catch (Exception e) {
                    if (onGetResponseData != null)
                        onGetResponseData.onGetData(null);
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                if (onGetResponseData != null)
                    onGetResponseData.onGetData(null);
            }
        });

    }

    /**
     * 表单提交
     *
     * @param url   路径
     * @param datas 参数数据
     * @param files 文件数据
     * @return result 返回数据
     * @throws Exception
     */
    public void postRequestByXUtils(String url, Map<String, String> datas,
                                    Map<String, String> files) throws Exception {
        // final String result = null;
        RequestParams params = null;
        addNecessaryParas(params);
        //params.addHeader();
        if (datas == null || files == null) {

        } else {
            params = new RequestParams();
            for (String key : datas.keySet()) {
                params.addBodyParameter(key, datas.get(key));
            }
            for (String key : files.keySet()) {
                if ("".equals(files.get(key)) || files.get(key) == null)
                    continue;
                params.addBodyParameter(key, new File(files.get(key)));
            }
        }

        HttpUtils http = new HttpUtils();
        http.configTimeout(300000);

        http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

            @Override
            public void onStart() {
                LogUtils.d("conn...");
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                onGetResponseData.onGetData(result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
            }
        });

    }

    private void addNecessaryParas(RequestParams params) {
        //必传参数
        //String sign = MD5.Md5(UrlConstant.app_key + UrlConstant.app_secret);
//        params.addHeader("appKey", UrlConstant.app_key);
//        params.addHeader("appSecret", UrlConstant.app_secret);
        //params.addHeader("sign", sign);
//        params.addHeader("source", "scan");
        params.addHeader("X-Requested-With", "XMLHttpRequest");
        String sessionId = GlobleCache.getInst().getCacheSessionId();
        if (!TextUtils.isEmpty(sessionId)) {
            //params.addHeader(ResultCodeConstant.SessionKeyName, sessionId);
            //params.addHeader("Cookie", ResultCodeConstant.SessionKeyName + "=" + sessionId);
            params.addHeader("Cookie", ResultCodeConstant.SessionKeyName + "=" + sessionId + ";" + "source=7");
        } else {
            params.addHeader("Cookie", ResultCodeConstant.SessionKeyName + "=;source=7");
        }
    }

    public interface OnGetResponseData {
        public void onGetData(String result);

    }
}
