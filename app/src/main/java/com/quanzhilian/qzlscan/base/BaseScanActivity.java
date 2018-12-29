package com.quanzhilian.qzlscan.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.quanzhilian.qzlscan.R;
import com.quanzhilian.qzlscan.activities.LoginActivity;
import com.quanzhilian.qzlscan.application.CustomerApplication;
import com.quanzhilian.qzlscan.common.MessageCenter;
import com.quanzhilian.qzlscan.dbmanager.DBManager;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryModel;
import com.quanzhilian.qzlscan.models.domain.SimpleRepositoryPositionModel;
import com.quanzhilian.qzlscan.models.enums.MessageWhat;
import com.quanzhilian.qzlscan.result.JsonRequestResult;
import com.quanzhilian.qzlscan.result.ResultCodeToast;
import com.quanzhilian.qzlscan.scan.ClientConfig;
import com.quanzhilian.qzlscan.utils.AppUtils;
import com.quanzhilian.qzlscan.utils.GlobleCache;
import com.quanzhilian.qzlscan.utils.HttpClientUtils;
import com.quanzhilian.qzlscan.utils.NetWorkUtils;
import com.quanzhilian.qzlscan.utils.StringUtils;
import com.quanzhilian.qzlscan.utils.UrlConstant;
import com.quanzhilian.qzlscan.utils.UrlUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hushouqi on 16/3/17.
 */
public class BaseScanActivity extends Activity {
    public static int DEVICE_MODEL = 0;

    public String text = "";
    public MediaPlayer player;
    public Vibrator vibrator;
    private String firstCodeStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected TextView textView(int id) {
        return (TextView) findViewById(id);
    }

    protected ImageView imageView(int id) {
        return (ImageView) findViewById(id);
    }

    protected void finish(int id) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void invisible(int id) {
        findViewById(id).setVisibility(View.INVISIBLE);
    }

    /* 是否登录，仅判断本地登录状态*/
    protected void isLogin(BaseScanActivity currentActivity) {
        String sessonId = GlobleCache.getInst().getCacheSessionId();

        if (TextUtils.isEmpty(sessonId)) {
            toLoginActivity(currentActivity);
        }
    }

    /* 是否登录，仅判断本地登录状态*/
    protected boolean isLogined(BaseScanActivity currentActivity) {
        String sessonId = GlobleCache.getInst().getCacheSessionId();

        if (TextUtils.isEmpty(sessonId)) {
            return false;
        }
        return true;
    }

    /*是否在线，判断服务端登录状态 */
    protected boolean isOnlineViaesult(final Activity currentActivity, JsonRequestResult jsonRequestResult) {

        //if (jsonRequestResult.getCode() == -2) {
        if (jsonRequestResult.getCode() == -1) {
            String toastText = ResultCodeToast.getInst().getToastText("-2");
            new AlertDialog.Builder(currentActivity).setTitle("系统提示")
                    .setMessage(toastText)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            removeLocalSession(currentActivity);
                            toLoginActivity(currentActivity);
                        }
                    }).show();


            return false;
        }
        return true;

    }

    /*退出登录*/
    protected void logout(BaseScanActivity currentActivity) {

        boolean removeCacheResult = false;
        String sessionId = GlobleCache.getInst().getCacheSessionId();


        try {
            removeCacheResult = removeLocalSession(currentActivity);
            /*请求服务端*/
            HttpClientUtils httpClientUtils = new HttpClientUtils();
            String requestUrl = UrlUtils.getFullUrl(UrlConstant.url_logout) + "&sessionId=" + sessionId;
            httpClientUtils.postRequest(requestUrl, null);

        } catch (Exception ex) {

        }
        if (removeCacheResult)
            toLoginActivity(currentActivity);

    }

    /* 清理本地session信息*/
    private boolean removeLocalSession(Activity currentActivity) {
        boolean removeCacheResult = false;
        //清理本地缓存信息
        try {
            GlobleCache.getInst().sessionIdDestroy();
            GlobleCache.getInst().sessionDestroy();
            removeCacheResult = true;
        } catch (Exception ex) {
            forToast(R.string.app_run_code_error);
        }
        return removeCacheResult;

    }

    /*跳转至登录activity*/
    protected void toLoginActivity(Activity currentActivity) {
        Intent intent = new Intent();

        intent.setClass(currentActivity.getBaseContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //启动Activity
        currentActivity.startActivityForResult(intent, 1);
    }

    /**
     * 显示提示信息
     *
     * @param text
     */
    protected void forToast(String text) {
        AppUtils.showToast(getApplicationContext(), text, Toast.LENGTH_SHORT);
    }

    protected void forToast(int resId) {
        AppUtils.showToast(getApplicationContext(), resId, Toast.LENGTH_SHORT);
    }

    /**
     * 选择仓库
     *
     * @param activity
     */
    protected void selectRepository(boolean showCancel, final Handler mHandler, final Activity activity, List<SimpleRepositoryModel> repositoryModelList) {
        LinearLayout linearLayoutMain = new LinearLayout(activity);//自定义一个布局文件
        linearLayoutMain.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ListView listView = new ListView(activity);//this为获取当前的上下文
        listView.setFadingEdgeLength(0);
        List<Map<String, String>> nameList = new ArrayList<Map<String, String>>();//建立一个数组存储listview上显示的数据
        for (int m = 0; m < repositoryModelList.size(); m++) {//initData为一个list类型的数据源
            String repositoryName = repositoryModelList.get(m).getRepositoryName();
            Integer repositoryId = repositoryModelList.get(m).getRepositoryId();
            Map<String, String> nameMap = new HashMap<String, String>();
            nameMap.put("repositoryName", repositoryName);
            nameMap.put("repositoryId", repositoryId.toString());
            nameList.add(nameMap);
        }

        SimpleAdapter adapter =
                new SimpleAdapter(activity, nameList, R.layout.item_repository_list, new String[]{"repositoryName", "repositoryId"}, new int[]{R.id.tv_select_repository_name, R.id.tv_select_repository_id});
        listView.setAdapter(adapter);

        linearLayoutMain.addView(listView, -1, -2);//往这个布局中加入listview

        final AlertDialog dialog = new AlertDialog.Builder(activity)
                .setIcon(getResources().getDrawable(R.mipmap.iconfont_tishi))
                .setTitle("请选择仓库").setView(linearLayoutMain)//在这里把写好的这个listview的布局加载dialog中
                .create();
        if (showCancel) {
            dialog.setButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        dialog.setCanceledOnTouchOutside(showCancel);//使除了dialog以外的地方不能被点击
        dialog.setCancelable(showCancel);
        dialog.show();
        //AppUtils.dialogTitleLineColor(dialog, getResources().getColor(R.color.default_color));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//响应listview中的item的点击事件

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                TextView tv_select_repository_name = (TextView) arg1.findViewById(R.id.tv_select_repository_name);//取得每条item中的textview控件
                TextView tv_select_repository_id = (TextView) arg1.findViewById(R.id.tv_select_repository_id);//取得每条item中的textview控件
                String repositoryName = tv_select_repository_name.getText().toString();
                String repositoryId = tv_select_repository_id.getText().toString();
                saveRepositoryForSP(mHandler, activity, repositoryId, repositoryName);
                dialog.cancel();
            }
        });
    }

    protected void selectRepositoryPosition(final Handler mHandler, final Activity activity) {
        LinearLayout linearLayoutMain = new LinearLayout(activity);//自定义一个布局文件
        linearLayoutMain.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ListView listView = new ListView(activity);//this为获取当前的上下文
        listView.setFadingEdgeLength(0);
        List<Map<String, String>> nameList = new ArrayList<Map<String, String>>();//建立一个数组存储listview上显示的数据
        List<SimpleRepositoryPositionModel> repositoryPositionModelList = DBManager.getInstance(activity).queryRepositoryPositionList(GlobleCache.getInst().getCacheRepositoryId());
        for (int m = 0; m < repositoryPositionModelList.size(); m++) {//initData为一个list类型的数据源
            String name = repositoryPositionModelList.get(m).getName();
            Integer positionId = repositoryPositionModelList.get(m).getRepositoryPositionId();
            Map<String, String> nameMap = new HashMap<String, String>();
            nameMap.put("name", name);
            nameMap.put("repositoryPositionId", positionId.toString());
            nameList.add(nameMap);
        }

        SimpleAdapter adapter =
                new SimpleAdapter(activity, nameList, R.layout.item_repository_position_list, new String[]{"name", "repositoryPositionId"}, new int[]{R.id.tv_select_repository_position_name, R.id.tv_select_repository_position_id});
        listView.setAdapter(adapter);

        linearLayoutMain.addView(listView, -1, -2);//往这个布局中加入listview

        final AlertDialog dialog = new AlertDialog.Builder(activity)
                .setIcon(getResources().getDrawable(R.mipmap.iconfont_tishi))
                .setTitle("请选择库位").setView(linearLayoutMain)//在这里把写好的这个listview的布局加载dialog中
                .create();
        dialog.setButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);//使除了dialog以外的地方不能被点击
        dialog.setCancelable(true);
        dialog.show();
        //AppUtils.dialogTitleLineColor(dialog, getResources().getColor(R.color.default_color));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//响应listview中的item的点击事件

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                TextView tv_select_repository_position_name = (TextView) arg1.findViewById(R.id.tv_select_repository_position_name);//取得每条item中的textview控件
                TextView tv_select_repository_position_id = (TextView) arg1.findViewById(R.id.tv_select_repository_position_id);//取得每条item中的textview控件
                String positionName = tv_select_repository_position_name.getText().toString();
                String positionId = tv_select_repository_position_id.getText().toString();
                Message msg = new Message();
                msg.what = MessageWhat.MessageType.QUERY_POSITION_SUCCESS;
                msg.obj = positionName;
                msg.arg1 = Integer.parseInt(positionId);
                mHandler.sendMessage(msg);
                dialog.cancel();
            }
        });
    }

    /**
     * 通过SharedPreferences保存选择的仓库信息
     *
     * @param repositoryId
     * @param repositoryName
     */
    private void saveRepositoryForSP(final Handler mHandler, Activity activity, String repositoryId, String repositoryName) {
        if (repositoryId != null && repositoryName != null) {
            GlobleCache.getInst().saveRepository(repositoryId, repositoryName);
            mHandler.sendEmptyMessageDelayed(1, 300);
        }
    }

    /**
     * 获取仓库数据
     */
    protected void getRepositoryList(final Activity activity, final Handler mHandler, final boolean showCancel) {
        List<SimpleRepositoryModel> repositoryList = DBManager.getInstance(activity).queryRepositoryList(GlobleCache.getInst().getScmId());
        if (repositoryList != null) {
            selectRepository(showCancel, mHandler, activity, repositoryList);
        }
    }

    /**
     * 跳转页面
     *
     * @param activity
     */
    public void jumpActivity(Class activity) {
        Intent intent = new Intent(BaseScanActivity.this, activity);
        startActivity(intent);
    }

    /**
     * 跳转页面(传参数)
     *
     * @param activity
     */
    public void jumpActivity(Class activity, Bundle bundle) {
        Intent intent = new Intent(BaseScanActivity.this, activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
