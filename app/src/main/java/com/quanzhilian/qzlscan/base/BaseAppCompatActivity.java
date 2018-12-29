package com.quanzhilian.qzlscan.base;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.quanzhilian.qzlscan.utils.AppUtils;

/**
 * Created by yangtm on 2018/2/1.
 */

public class BaseAppCompatActivity extends AppCompatActivity {
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
}
