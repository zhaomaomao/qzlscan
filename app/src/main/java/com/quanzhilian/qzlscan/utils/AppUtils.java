package com.quanzhilian.qzlscan.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.quanzhilian.qzlscan.models.domain.SimpleSessionModel;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/3/31 0031.
 */
public class AppUtils {
    /**
     * Toast提示显示
     */
    private static Toast mToast = null;

    public static void showToast(Context context, String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    public static void showToast(Context mContext, int resId, int duration) {
        showToast(mContext, mContext.getResources().getString(resId), duration);
    }

    /**
     * 价格格式化
     *
     * @param inputPrice
     * @return
     */
    public static BigDecimal formatPrice(BigDecimal inputPrice) {
        return inputPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 价格格式化(字符串)
     *
     * @param inputPrice
     * @return
     */
    public static String formatPriceToString(BigDecimal inputPrice) {
        return formatPrice(inputPrice).toString();
    }


    /**
     * 修改AlertDialog蓝色线条的颜色
     *
     * @param dialog
     * @param color
     */
    public static void dialogTitleLineColor(Dialog dialog, int color) {
        Context context = dialog.getContext();
        int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = dialog.findViewById(divierId);
        divider.setBackgroundColor(color);
    }

    /**
     * 隐藏AlertDialog蓝色线条
     *
     * @param dialog
     */
    public static void dialogTitleLineHide(Dialog dialog) {
        Context context = dialog.getContext();
        int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = dialog.findViewById(divierId);
        divider.setVisibility(View.GONE);
    }

    /**
     * 日期格式化
     *
     * @param mContext
     * @param date
     * @return
     */
    public static String myDateFormat(Context mContext, Date date) {


        return DateFormat.getDateFormat(mContext).format(date);
    }

    /**
     * 日期格式化，效果同上
     *
     * @param date
     * @return
     */
    public static String myDateFormat(Date date) {

        return SimpleDateFormat.getDateInstance().format(date);
    }

    /**
     * 字符串日期格式化为Date类型
     *
     * @param dateStr
     * @return
     */
    public static Date myDateFormat(String dateStr) {

        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串日期格式化为Date类型
     *
     * @param dateStr
     * @return
     */
    public static Date myDateFormat(String dateStr,String format) {

        try {
            return new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 字符串毫秒数日期格式化为字符串日期类型
     *
     * @param dateStr
     * @return
     */
    public static String myLongDateFormat(String dateStr) {

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(dateStr)));
    }

    /**
     * 获取当前时间
     */
    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
    }

    /**
     * 日期计算
     *
     * @param field
     * @param count
     * @return
     */
    public static Date dateOperation(Date targetDate, int field, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(targetDate);
        calendar.add(field, count);
        return calendar.getTime();
    }

    /**
     * 数字输入
     *
     * @param editText
     */
    public static void setNumberPoint(final EditText editText, final int digit, final double maxNumber, final double minNumber) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > digit) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + (digit + 1));
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(digit);
                }

                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
                if (!TextUtils.isEmpty(editText.getText().toString().trim())
                        && maxNumber < Double.parseDouble(editText.getText().toString().trim())) {
                    editText.setText(maxNumber + "");
                }
                if (!TextUtils.isEmpty(editText.getText().toString().trim())
                        && minNumber > Double.parseDouble(editText.getText().toString().trim())) {
                    editText.setText(minNumber + "");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

    }

    /**
     * 获取本地缓存账户信息
     */
    public static SimpleSessionModel getSessionModel() {
        if (GlobleCache.getInst() != null) {
            if (GlobleCache.getInst().getCacheSessionModel() != null) {
                return GlobleCache.getInst().getCacheSessionModel();
            }
        }
        return null;
    }

    /**
     * 当金额大于11位时只显示前8位
     *
     * @param num 金额
     * @return num
     */
    public static String getShowNum(String num) {
        if (TextUtils.isEmpty(num) || num.equals("null")) {
            num = "0.00";
        } else if (num.length() > 11) {
            num = num.substring(0, 7) + "...";
        }
        return num;
    }

    /**
     * 判断当前网络是否可用(增加权限android.permission.ACCESS_NETWORK_STATE)
     *
     * @param con
     * @return (false:不可以;true:可以)
     */
    public static boolean isNetworkAvailable(Context con) {
        ConnectivityManager cm = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo == null) {
            return false;
        }
        if (netinfo.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * 0.5秒内不能同时点击按钮
     *
     * @return
     */
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getDisplayWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

}
