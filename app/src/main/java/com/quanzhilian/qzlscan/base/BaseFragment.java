package com.quanzhilian.qzlscan.base;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.quanzhilian.qzlscan.utils.AppUtils;

/**
 * Created by yangtm on 2018/2/1.
 */

public class BaseFragment extends Fragment {
    /**
     * 显示提示信息
     *
     * @param text
     */
    protected void forToast(String text) {
        AppUtils.showToast(getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT);
    }

    protected void forToast(int resId) {
        AppUtils.showToast(getActivity().getApplicationContext(), resId, Toast.LENGTH_SHORT);
    }
}
