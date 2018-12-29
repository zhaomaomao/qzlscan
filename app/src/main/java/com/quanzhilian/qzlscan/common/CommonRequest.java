package com.quanzhilian.qzlscan.common;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.quanzhilian.qzlscan.R;
import com.quanzhilian.qzlscan.dbmanager.DBManager;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryModel;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryPositionModel;
import com.quanzhilian.qzlscan.models.enums.EnumQueryType;
import com.quanzhilian.qzlscan.models.enums.MessageWhat;
import com.quanzhilian.qzlscan.result.JsonRequestResult;
import com.quanzhilian.qzlscan.utils.AppUtils;
import com.quanzhilian.qzlscan.utils.GlobleCache;
import com.quanzhilian.qzlscan.utils.HttpClientUtils;
import com.quanzhilian.qzlscan.utils.NetWorkUtils;
import com.quanzhilian.qzlscan.utils.UrlConstant;
import com.quanzhilian.qzlscan.utils.UrlUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangtm on 2018/2/23.
 */

public class CommonRequest {

    static int GET_LIST_SUCCESS = 10000;
    static int GET_QUERY_TIEM_SUCCESS = 20000;

    /**
     *
     * @param activity
     * @param mHandler
     */
    public static void getServiceQueryTime(final Activity activity, final Handler mHandler) {
        if (NetWorkUtils.isNetWork(activity)) {

            HttpClientUtils httpClientUtil = new HttpClientUtils();
            httpClientUtil.setOnGetData(new HttpClientUtils.OnGetResponseData() {
                                            @Override
                                            public void onGetData(String result) {
                                                if (result == null) {
                                                    AppUtils.showToast(activity, R.string.server_connect_error, Toast.LENGTH_SHORT);
                                                } else {
                                                    try {
                                                        JsonRequestResult jsonRequestResult = JsonRequestResult.toJsonRequestResult(result);
                                                        Message message = new Message();
                                                        message.what = MessageWhat.MessageType.GET_QUERY_TIEM_SUCCESS;
                                                        message.obj = jsonRequestResult.getObj();
                                                        mHandler.sendMessage(message);
                                                    } catch (Exception ex) {
                                                        AppUtils.showToast(activity, R.string.json_parser_failed, Toast.LENGTH_SHORT);
                                                    }
                                                }

                                            }
                                        }
            );
            try {
                /*请求服务端*/
                String requestUrl = UrlUtils.getFullUrl(UrlConstant.url_get_service_time);
                httpClientUtil.postRequest(requestUrl, null);
            } catch (Exception ex) {
                AppUtils.showToast(activity, R.string.server_connect_error, Toast.LENGTH_SHORT);
            } finally {

            }
        } else {
            //设备无连接网络
            AppUtils.showToast(activity, R.string.network_not_connected, Toast.LENGTH_SHORT);
        }
    }
}
