package com.quanzhilian.qzlscan.update;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.quanzhilian.qzlscan.R;
import com.quanzhilian.qzlscan.application.CustomerApplication;
import com.quanzhilian.qzlscan.models.domain.SysSetting;
import com.quanzhilian.qzlscan.models.domain.UpdateVersionModel;
import com.quanzhilian.qzlscan.result.JsonRequestResult;
import com.quanzhilian.qzlscan.utils.HttpClientUtils;
import com.quanzhilian.qzlscan.utils.NetWorkUtils;
import com.quanzhilian.qzlscan.utils.ParseTimeUtil;
import com.quanzhilian.qzlscan.utils.UrlConstant;
import com.quanzhilian.qzlscan.utils.UrlUtils;
import com.quanzhilian.qzlscan.views.CustomDialog;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateVersion {
    private final static String TAG = UpdateVersion.class.getSimpleName();

    private String versionDownload;
    private String versionAppName = "qzlScan.apk";
    private NumberProgressBar mProgress;
    private Boolean cancelUpdate = false;
    private Dialog mDownloadDialog;
    private String mSavePath;
    private int progress;
    private static final int DOWNLOAD = 1;
    private static final int DOWNLOAD_FINISH = 2;
    private Activity mContext;

    public UpdateVersion(Activity context) {
        mContext = context;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case DOWNLOAD:
                    mProgress.setProgress(progress);
                    break;
                case DOWNLOAD_FINISH:
                    installApk();
                    break;
            }
        }

        ;
    };

    public boolean ischeckUpdateTime() {
        long updateTime = CustomerApplication.getInstance().isUpdateVersionTime;
        if (updateTime == 0 ||
                ParseTimeUtil.getCurrentTimeInLong() - updateTime > 24 * 60 * 60 * 1000) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     */
    public void checkUpdateVersion(final boolean isClick) {
        if (NetWorkUtils.isNetWork(mContext)) {
            final String version = CustomerApplication.getAppVersionName(mContext);

            HttpClientUtils httpClientUtil = new HttpClientUtils();
            int pageIndex = 1;
            try {
                   /*请求服务端*/
                Map<String, String> paras = new HashMap<String, String>();
                String requestUrl = UrlUtils.getFullUrl(UrlConstant.APP_UPDATE_CONFIG);
                httpClientUtil.postRequest(requestUrl, paras);

            } catch (Exception ex) {
                Toast.makeText(mContext, R.string.server_connect_error, Toast.LENGTH_SHORT).show();
            } finally {

            }
            httpClientUtil.setOnGetData(new HttpClientUtils.OnGetResponseData() {
                                            @Override
                                            public void onGetData(String result) {
                                                if (result == null) {
                                                    Toast.makeText(mContext, R.string.server_connect_error, Toast.LENGTH_SHORT).show();
                                                } else {
                                                    try {
                                                        JsonRequestResult jsonRequestResult = JsonRequestResult.toJsonRequestResult(result);
                                                        if (jsonRequestResult.getCode() == 1) {
                                                            List<SysSetting> settingList = jsonRequestResult.getResultBeanList(SysSetting.class);
                                                            UpdateVersionModel updateVersionModel = new UpdateVersionModel();
                                                            if (settingList != null) {
                                                                for (SysSetting sysSetting :
                                                                        settingList) {
                                                                    if (sysSetting.getCode().equals("android_download_url")) {
                                                                        updateVersionModel.android_download_url = sysSetting.getValue();
                                                                    } else if (sysSetting.getCode().equals("android_upgrade_mode")) {
                                                                        updateVersionModel.android_upgrade_mode = sysSetting.getValue();
                                                                    } else if (sysSetting.getCode().equals("android_version")) {
                                                                        updateVersionModel.android_version = sysSetting.getValue();
                                                                    }
                                                                }

                                                                if (isClick || ischeckUpdateTime()) {
                                                                    if (updateVersionModel.android_version != null && !version.equals(updateVersionModel.android_version)) {
                                                                        CustomerApplication.getInstance().isUpdateVersionTime = ParseTimeUtil.getCurrentTimeInLong();
                                                                        showUpdateDialog(updateVersionModel);
                                                                    }
                                                                } else {
                                                                    if (isClick) {
                                                                        Toast.makeText(mContext, "当前已是最新版本", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            } else {
                                                                Toast.makeText(mContext, "没有获取到更新信息", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            if (isClick) {
                                                                Toast.makeText(mContext, "没有获取到更新信息", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    } catch (Exception ex) {
                                                        Toast.makeText(mContext, R.string.json_parser_failed, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
                                        }
            );
        } else //设备无连接网络
        {
            Toast.makeText(mContext, R.string.network_not_connected, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 显示更新信息 Dialog
     *
     * @param data
     */
    private void showUpdateDialog(final UpdateVersionModel data) {
        try {
            versionDownload = data.android_download_url+"?v="+data.android_version;
            final String version = CustomerApplication.getAppVersionName(mContext);
            final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialogStyle);
            dialog.setCancelable(false);
            View view = mContext.getLayoutInflater().inflate(R.layout.dialog_updata_version, null);
            dialog.setView(view);
            dialog.show();
            String upgradeMode = data.android_upgrade_mode;
            TextView tvCancle = (TextView) view.findViewById(R.id.tv_cancle);
            TextView textView = (TextView) view.findViewById(R.id.tv_content);
            textView.setText("本次更新内容：\n1.系统功能优化；\n2.已知bug修复；\n\n当前版本号："+version+"\n更新版本号："+data.android_version);
            View viewBg = view.findViewById(R.id.view_bg);
            if (upgradeMode != null && "1".equals(upgradeMode)) {
                CustomerApplication.getInstance().isUpdateVersionTime = 0;
                tvCancle.setVisibility(View.GONE);
                viewBg.setVisibility(View.GONE);
            } else {
                tvCancle.setVisibility(View.VISIBLE);
                tvCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
            view.findViewById(R.id.tv_update).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                    // 检查存储卡是否有读写权限
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        Toast.makeText(mContext, "存储卡没有读写权限,下载失败,请插入SD卡", Toast.LENGTH_SHORT).show();
                    } else {
                        // 显示下载对话框
                        showDownloadDialog(data);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDownloadDialog(UpdateVersionModel data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        //builder.setTitle(R.string.soft_updating);
        builder.setCancelable(false);

        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_update_app, null);
        mProgress = (NumberProgressBar) view.findViewById(R.id.update_progress);
        builder.setView(view);

        String upgradeMode = data.android_upgrade_mode;
        if (upgradeMode != null && "2".equals(upgradeMode)) {
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    cancelUpdate = true;
                }
            });
        }
        mDownloadDialog = builder.create();
        mDownloadDialog.show();

        downloadApk();
    }

    private void downloadApk() {
        new downloadApkThread().start();
    }

    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            try {
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + "download";
                    URL url = new URL(versionDownload);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    int length = conn.getContentLength();
                    InputStream is = conn.getInputStream();
                    File file = new File(mSavePath);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, versionAppName);
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    byte buf[] = new byte[1024];
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        progress = (int) (((float) count / length) * 100);
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0) {
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);
                    fos.close();
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            mDownloadDialog.dismiss();
        }
    }

    private void installApk() {
        File apkfile = new File(mSavePath, versionAppName);
        if (!apkfile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
