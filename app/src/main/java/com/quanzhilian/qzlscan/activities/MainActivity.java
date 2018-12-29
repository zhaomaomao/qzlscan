package com.quanzhilian.qzlscan.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quanzhilian.qzlscan.R;
import com.quanzhilian.qzlscan.activities.changepositon.MainChangeActivity;
import com.quanzhilian.qzlscan.activities.inrepository.MainInRepositoryActivity;
import com.quanzhilian.qzlscan.activities.machining.MainMachiningActivity;
import com.quanzhilian.qzlscan.activities.outrepository.MainOutRepositoryActivity;
import com.quanzhilian.qzlscan.activities.outrepository.OutRepositoryDetailActivity;
import com.quanzhilian.qzlscan.application.CustomerApplication;
import com.quanzhilian.qzlscan.base.BaseActivity;
import com.quanzhilian.qzlscan.base.BaseScanActivity;
import com.quanzhilian.qzlscan.dbmanager.DBManager;
import com.quanzhilian.qzlscan.models.domain.SimpleMemberModel;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryModel;
import com.quanzhilian.qzlscan.models.domain.SimpleSessionModel;
import com.quanzhilian.qzlscan.models.params.LoginRequestParamModel;
import com.quanzhilian.qzlscan.result.JsonRequestResult;
import com.quanzhilian.qzlscan.scan.ClientConfig;
import com.quanzhilian.qzlscan.update.UpdateVersion;
import com.quanzhilian.qzlscan.utils.GlobleCache;
import com.quanzhilian.qzlscan.utils.HttpClientUtils;
import com.quanzhilian.qzlscan.utils.JsonUtils;
import com.quanzhilian.qzlscan.utils.NetWorkUtils;
import com.quanzhilian.qzlscan.utils.StringUtils;
import com.quanzhilian.qzlscan.utils.UrlConstant;
import com.quanzhilian.qzlscan.utils.UrlUtils;
import com.scandecode.ScanDecode;
import com.scandecode.inf.ScanInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.security.AccessController.getContext;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_company_name;//公司名称
    private TextView tv_operator;//操作员
    private TextView tv_repository_name;//当前仓库名
    private TextView tv_scan_name;//当前仓库名

    private LinearLayout ll_in_scan;//入库扫码
    private LinearLayout ll_out_scan;//出库扫码
    private LinearLayout ll_change;//库位调整
    private LinearLayout ll_machining;//加工取料
    private ImageView iv_exit;//退出

    private ProgressDialog progressDialog;

    private SharedPreferences repositorySP = null;

    private static final int SHOW_REPOSITORY = 1;
    private static final int QUERY_SUCCESS = 10000;
    private static final int TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        progressDialog = new ProgressDialog(MainActivity.this);
        tv_company_name = (TextView) findViewById(R.id.tv_company_name);
        tv_operator = (TextView) findViewById(R.id.tv_operator);
        tv_repository_name = (TextView) findViewById(R.id.tv_repository_name);
        tv_scan_name = (TextView) findViewById(R.id.tv_scan_name);

        ll_in_scan = (LinearLayout) findViewById(R.id.ll_in_scan);
        ll_out_scan = (LinearLayout) findViewById(R.id.ll_out_scan);
        ll_change = (LinearLayout) findViewById(R.id.ll_change);
        ll_machining = (LinearLayout) findViewById(R.id.ll_machining);

        iv_exit = (ImageView) findViewById(R.id.iv_exit);

        ll_in_scan.setOnClickListener(this);
        ll_out_scan.setOnClickListener(this);
        ll_change.setOnClickListener(this);
        ll_machining.setOnClickListener(this);
        iv_exit.setOnClickListener(this);
        //获取用户信息
        getMemberInfo();
        getRepositoryList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_in_scan:
                jumpActivity(MainInRepositoryActivity.class);
                break;
            case R.id.ll_out_scan:
                jumpActivity(MainOutRepositoryActivity.class);
                break;
            case R.id.ll_change:
                jumpActivity(MainChangeActivity.class);
                break;
            case R.id.ll_machining:
                jumpActivity(MainMachiningActivity.class);
                break;
            case R.id.iv_exit:
                new android.support.v7.app.AlertDialog.Builder(MainActivity.this)
                        .setTitle("提示")
                        .setMessage("确认要退出登录？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                logout(MainActivity.this);
                            }
                        })
                        .create().show();
                break;
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            // progressDialog.dismiss();
            switch (msg.what) {
                case SHOW_REPOSITORY:
                    //刷新仓库
                    showRepository();
                    break;
                case QUERY_SUCCESS:
                    //获取当前仓库信息，获取没有仓库弹出仓库选择框
                    String repositoryName = GlobleCache.getInst().getCacheRepositoryName();
                    String repositoryId = GlobleCache.getInst().getCacheRepositoryId();
                    if (!TextUtils.isEmpty(repositoryName) && !TextUtils.isEmpty(repositoryId)) {
                        showRepository();
                    } else {
                        getRepositoryList(MainActivity.this, mHandler, false);
                    }
                    break;
            }
        }

    };

    /**
     * 从repositorySP显示仓库信息
     */
    private void showRepository() {
        String repositoryName = GlobleCache.getInst().getCacheRepositoryName();
        tv_repository_name.setText("仓库：" + repositoryName);
    }

    /**
     * 更新APP
     */
    private void updateVersion() {
        UpdateVersion updateVersion = new UpdateVersion(MainActivity.this);
        if (updateVersion.ischeckUpdateTime()) {
            updateVersion.checkUpdateVersion(false);
        }
    }

    /**
     * 用户登录
     */
    private void getMemberInfo() {
        if (NetWorkUtils.isNetWork(this.getBaseContext())) {
            final String version = CustomerApplication.getAppVersionName(this.getBaseContext());
            HttpClientUtils httpClientUtil = new HttpClientUtils();
            httpClientUtil.setOnGetData(new HttpClientUtils.OnGetResponseData() {
                                            @Override
                                            public void onGetData(String result) {
                                                progressDialog.dismiss(); //关闭加载进度动画
                                                if (result == null) {
                                                    forToast(R.string.server_connect_error);
                                                } else {
                                                    try {
                                                        JsonRequestResult jsonRequestResult = JsonRequestResult.toJsonRequestResult(result);
                                                        /*登录成功，保存Session至本地*/
                                                        if (jsonRequestResult.getCode() == 1) {
                                                            SimpleMemberModel member = jsonRequestResult.getResultObjBean(SimpleMemberModel.class);
                                                            if (member != null) {
                                                                tv_company_name.setText(member.getCompanyName());
                                                                tv_operator.setText("操作人：" + member.getContact());
                                                                tv_scan_name.setText("物料扫码管理 V"+version);
                                                                GlobleCache.getInst().saveCompany(member.getCompanyName(),member.getContact());
                                                            }
                                                        } else {
                                                            isOnlineViaesult(MainActivity.this, jsonRequestResult);
                                                            //验证失败，提示用户
                                                            forToast(jsonRequestResult.getMsg());
                                                        }
                                                    } catch (Exception ex) {
                                                        forToast(R.string.json_parser_failed);
                                                    }


                                                }

                                            }
                                        }
            );
            try {
                /*请求服务端*/
                String requestUrl = UrlUtils.getFullUrl(UrlConstant.url_get_member_info);
                httpClientUtil.postRequest(requestUrl, null);
                progressDialog.show();//启动加载进度动画
            } catch (Exception ex) {
                progressDialog.dismiss(); //关闭加载进度动画
                forToast(R.string.server_connect_error);
            } finally {

            }
        } else {
            //设备无连接网络
            forToast(R.string.network_not_connected);
        }
    }


    /**
     * 从服务器获取仓库信息
     */
    protected void getRepositoryList() {
        if (NetWorkUtils.isNetWork(this.getBaseContext())) {

            HttpClientUtils httpClientUtil = new HttpClientUtils();
            httpClientUtil.setOnGetData(new HttpClientUtils.OnGetResponseData() {
                                            @Override
                                            public void onGetData(String result) {
                                                if (result == null) {
                                                    forToast(R.string.server_connect_error);
                                                } else {
                                                    try {
                                                        JsonRequestResult jsonRequestResult = JsonRequestResult.toJsonRequestResult(result);
                                                        /*登录成功，保存Session至本地*/
                                                        if (jsonRequestResult.getCode() == 1) {
                                                            List<SimpleRepositoryModel> repositoryList = jsonRequestResult.getResultBeanList(SimpleRepositoryModel.class);
                                                            if (repositoryList != null) {
                                                                DBManager.getInstance(MainActivity.this).insertRepository(repositoryList, GlobleCache.getInst().getScmId());
                                                                mHandler.sendEmptyMessage(QUERY_SUCCESS);
                                                            }
                                                        } else {
                                                            isOnlineViaesult(MainActivity.this, jsonRequestResult);
                                                        }
                                                    } catch (Exception ex) {
                                                        forToast(R.string.json_parser_failed);
                                                    }
                                                }

                                            }
                                        }
            );
            try {
                /*请求服务端*/
                String requestUrl = UrlUtils.getFullUrl(UrlConstant.url_get_repository_list);
                httpClientUtil.postRequest(requestUrl, null);
            } catch (Exception ex) {
                forToast(R.string.server_connect_error);
            } finally {

            }
        } else {
            //设备无连接网络
            forToast(R.string.network_not_connected);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateVersion();
        showRepository();
    }
}
