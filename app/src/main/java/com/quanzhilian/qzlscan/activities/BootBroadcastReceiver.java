package com.quanzhilian.qzlscan.activities;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import com.quanzhilian.qzlscan.activities.splashguide.SplashActivity;

/**
 * @Description 功能描述： 该类用来实现该app开机自动运行
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    /**
     *  可以实现开机自动打开软件并运行。
     */
//    @Override
//    public void onReceive(Context context, Intent intent) {
//
//        Log.d("XRGPS", "BootReceiver.onReceive: " + intent.getAction());
//        System.out.println("自启动程序即将执行");
//        //MainActivity就是开机显示的界面
//        Intent mBootIntent = new Intent(context, SplashActivity.class);
//        //下面这句话必须加上才能开机自动运行app的界面
//        mBootIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(mBootIntent);
//
//    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //屏幕唤醒
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.SCREEN_DIM_WAKE_LOCK, "StartupReceiver");
        wl.acquire();
        //屏幕解锁
        KeyguardManager km = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock K1=km.newKeyguardLock("StartupReceiver");
        //KeyguardManager.KeyguardLock K1=km.newKeyguardLock("");
// KeyguardManager.KeyguardLock K1=km.newKeyguardLock("unlock"); K1.disableKeyguard();

        Intent intent1 = new Intent(context, SplashActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent1);
 /* //开机启动 Intent intent1=new Intent(context,Grant.class); Toast.makeText(context,"启动成功",Toast.LENGTH_LONG).show(); intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//// intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);FLAG_ACTIVITY_CLEAR_TOP context.startActivity(intent1);
    Intent intent1 = new Intent(context, MainActivity.class);
     intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    context.startActivity(intent1);



 */
    }
}
