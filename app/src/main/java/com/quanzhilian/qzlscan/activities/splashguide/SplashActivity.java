package com.quanzhilian.qzlscan.activities.splashguide;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.widget.TextView;

import com.quanzhilian.qzlscan.R;
import com.quanzhilian.qzlscan.activities.BootBroadcastReceiver;
import com.quanzhilian.qzlscan.activities.LoginActivity;
import com.quanzhilian.qzlscan.activities.MainActivity;
import com.quanzhilian.qzlscan.activities.ScreenReceive;
import com.quanzhilian.qzlscan.application.CustomerApplication;
import com.quanzhilian.qzlscan.base.BaseActivity;

public class SplashActivity extends BaseActivity {
    private static final int GOTO_LOGIN_ACTIVITY = 0;
    private static final int GOTO_MAIN_ACTIVITY = 1;
    private boolean isFirstIn = false;
    private static final int TIME = 3000;
    private static final int GO_GUIDE = 1001;

//    private DevicePolicyManager dpm;
//    private ComponentName cpn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        init();

    }

    private void init() {
        if (isLogined(SplashActivity.this)) {
            mHandler.sendEmptyMessageDelayed(GOTO_MAIN_ACTIVITY, TIME);
        } else {
            mHandler.sendEmptyMessageDelayed(GOTO_LOGIN_ACTIVITY, TIME);
        }
    }

    private void initView() {
        TextView tv_splash_version_name = (TextView) findViewById(R.id.tv_splash_version_name);
        String versionName = getVersionName();
        //tv_splash_version_name.setText("版本号 " + versionName);


//        final PowerManager pm= (PowerManager)getSystemService(Context.POWER_SERVICE);
//        final PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK,"TAG");
//        wakeLock.acquire();
//        dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
//        cpn = new ComponentName(this, ScreenReceive.class);
//        final boolean  activity = dpm.isAdminActive(cpn);
//
//        if (!activity) {
//            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
//
//            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cpn);
//            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "one key lock screen need to activity");
//            startActivityForResult(intent, 0);
//
//            dpm.lockNow();
//            //android.os.Process.killProcess(android.os.Process.myPid());
//            wakeLock.release();
//
//        }
//        if (activity) {
//            dpm.lockNow();
//            wakeLock.release();
//            android.os.Process.killProcess(android.os.Process.myPid());
//        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            // progressDialog.dismiss();
            switch (msg.what) {
                case GOTO_LOGIN_ACTIVITY:
                    Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                    break;
                case GOTO_MAIN_ACTIVITY:
                    Intent mainActivityIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mainActivityIntent);
                    finish();
                    break;
            }
        }

    };

    private String getVersionName() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBackPressed() {

    }
}
