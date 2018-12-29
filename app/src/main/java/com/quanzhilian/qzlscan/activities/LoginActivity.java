package com.quanzhilian.qzlscan.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.quanzhilian.qzlscan.R;
import com.quanzhilian.qzlscan.activities.splashguide.SplashActivity;
import com.quanzhilian.qzlscan.base.BaseActivity;
import com.quanzhilian.qzlscan.models.domain.SimpleSessionModel;
import com.quanzhilian.qzlscan.models.params.LoginRequestParamModel;
import com.quanzhilian.qzlscan.result.JsonRequestResult;
import com.quanzhilian.qzlscan.result.ResultCodeToast;
import com.quanzhilian.qzlscan.utils.AppUtils;
import com.quanzhilian.qzlscan.utils.Constant;
import com.quanzhilian.qzlscan.utils.GlobleCache;
import com.quanzhilian.qzlscan.utils.HttpClientUtils;
import com.quanzhilian.qzlscan.utils.JsonUtils;
import com.quanzhilian.qzlscan.utils.NetWorkUtils;
import com.quanzhilian.qzlscan.utils.UrlConstant;
import com.quanzhilian.qzlscan.utils.UrlUtils;

import java.util.Map;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ProgressDialog progressDialog;//加载进度动画；
    private SharedPreferences sharedPreferences;

    private EditText et_login_account;
    private EditText et_login_password;
    private Button btn_login_click;

    private static final int GOTO_MAIN_ACTIVITY = 1;
    private static final int TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        et_login_account = (EditText) findViewById(R.id.login_account);
        et_login_password = (EditText) findViewById(R.id.login_pwd);
        btn_login_click = (Button) findViewById(R.id.btn_login_click);

        btn_login_click.setOnClickListener(this);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("获取系统数据中...");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_click:
                //用户登录
                if (AppUtils.isNetworkAvailable(LoginActivity.this)) {
                    if (!AppUtils.isFastDoubleClick()) {
                        if (checkInput()) {
                            login();
                        }
                    }
                } else {
                    forToast(Constant.NETWORKERROR);
                }
                break;
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            // progressDialog.dismiss();
            switch (msg.what) {
                case GOTO_MAIN_ACTIVITY:
                    //返回上一级
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                    break;
            }
        }

    };

    /**
     * 用户登录
     */
    private void login() {
        if (NetWorkUtils.isNetWork(this.getBaseContext())) {

            HttpClientUtils httpClientUtil = new HttpClientUtils();
            httpClientUtil.setOnGetData(new HttpClientUtils.OnGetResponseData() {
                                            @Override
                                            public void onGetData(String result) {
                                                progressDialog.dismiss(); //关闭加载进度动画
                                                if (result == null) {
                                                    forToast("连接服务器失败");
                                                } else {
                                                    try {
                                                        JsonRequestResult jsonRequestResult = JsonRequestResult.toJsonRequestResult(result);
                                                        /*登录成功，保存Session至本地*/
                                                        if (jsonRequestResult.getCode() == 1) {
                                                            SimpleSessionModel simpleSessionModel = new SimpleSessionModel();
                                                            simpleSessionModel.setSessionId(jsonRequestResult.getObj().toString());
                                                            if (simpleSessionModel != null) {
                                                                GlobleCache.getInst().storeSession(simpleSessionModel, jsonRequestResult.getMsg());
                                                                GlobleCache.getInst().repositoryDestroy();
                                                            }
                                                            //获取用户信息
                                                            mHandler.sendEmptyMessageDelayed(GOTO_MAIN_ACTIVITY, TIME);

                                                            forToast(R.string.login_success);
                                                        } else //验证失败，提示用户
                                                        {
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
                LoginRequestParamModel paramModel = getLoginRequestParamModel();
                if (paramModel != null) {
                    /*请求服务端*/
                    Map<String, String> paras = JsonUtils.beanToMap(paramModel);
                    String requestUrl = UrlUtils.getFullUrl(UrlConstant.url_login_validate);
                    httpClientUtil.postRequest(requestUrl, paras);
                    progressDialog.show();//启动加载进度动画
                }
            } catch (Exception ex) {
                progressDialog.dismiss(); //关闭加载进度动画
                //Toast.makeText(getApplicationContext(), R.string.server_connect_error, Toast.LENGTH_SHORT).show();
                forToast(R.string.server_connect_error);
            } finally {

            }
        } else //设备无连接网络
        {

            //Toast.makeText(getApplicationContext(), R.string.network_not_connected, Toast.LENGTH_SHORT).show();
            forToast(R.string.network_not_connected);
        }
    }

    /**
     * 验证登录信息
     */
    /**
     * 验证用户输入
     */
    private boolean checkInput() {
        if (TextUtils.isEmpty(et_login_account.getText().toString().trim())) {
            forToast(getResources().getString(R.string.login_user_name));
            return false;
        } else if (TextUtils.isEmpty(et_login_password.getText())) {
            forToast(getResources().getString(R.string.password));
            return false;
        }
        return true;
    }

    /*获取用户输入信息*/
    private LoginRequestParamModel getLoginRequestParamModel() {
        String inputLoginName = null;
        String inputPassword = null;

        LoginRequestParamModel paramModel = new LoginRequestParamModel();
        Map<String, String> paras = null;
        inputLoginName = et_login_account.getText().toString().trim();
        inputPassword = et_login_password.getText().toString();

        paramModel.setPassword(inputPassword);
        paramModel.setUsername(inputLoginName);
        return paramModel;

    }

}
