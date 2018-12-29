package com.quanzhilian.qzlscan.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Calendar;

/**
 * Created by zzxyer on 2017/6/1.
 */

public class FixedHoloDatePickerDialog extends DatePickerDialog {
    DatePicker datePicker;
    final Context context;
    private final Calendar mCalendar;

    @SuppressLint("NewApi")
    public FixedHoloDatePickerDialog(Context context,
                                     OnDateSetListener callBack, int year, int monthOfYear,
                                     int dayOfMonth) {
        super(context, callBack, year, monthOfYear, dayOfMonth);

        this.context = context;
        mCalendar = Calendar.getInstance();
        // Force spinners on Android 7.0 only (SDK 24).
        // Note: I'm using a naked SDK value of 24 here, because I'm
        // targeting SDK 23, and Build.VERSION_CODES.N is not available yet.
        // But if you target SDK >= 24, you should have it.
        if (Build.VERSION.SDK_INT == 24) {
            try {
                final Field field = this.findField(DatePickerDialog.class,
                        DatePicker.class, "mDatePicker");

                datePicker = (DatePicker) field.get(this);
                final Class<?> delegateClass = Class
                        .forName("android.widget.DatePicker$DatePickerDelegate");
                final Field delegateField = this.findField(DatePicker.class,
                        delegateClass, "mDelegate");

                final Object delegate = delegateField.get(datePicker);
                final Class<?> spinnerDelegateClass = Class
                        .forName("android.widget.DatePickerSpinnerDelegate");

                if (delegate.getClass() != spinnerDelegateClass) {
                    delegateField.set(datePicker, null);
                    datePicker.removeAllViews();

                    final Constructor spinnerDelegateConstructor = spinnerDelegateClass
                            .getDeclaredConstructor(DatePicker.class,
                                    Context.class, AttributeSet.class,
                                    int.class, int.class);
                    spinnerDelegateConstructor.setAccessible(true);

                    final Object spinnerDelegate = spinnerDelegateConstructor
                            .newInstance(datePicker, context, null,
                                    android.R.attr.datePickerStyle, 0);
                    delegateField.set(datePicker, spinnerDelegate);

                    datePicker.init(year, monthOfYear, dayOfMonth, this);
                    datePicker.setCalendarViewShown(false);
                    datePicker.setSpinnersShown(true);
                }
            } catch (Exception e) { /* Do nothing */
            }
        } else {
            datePicker = new DatePicker(context);
        }
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        super.onDateChanged(view, year, month, day);
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, day);
        String title = DateUtils.formatDateTime(context,
                mCalendar.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_SHOW_WEEKDAY
                        | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_ABBREV_MONTH
                        | DateUtils.FORMAT_ABBREV_WEEKDAY);
        if (hasNoDay)
            title = year + "年" + (month + 1) + "月";

        this.setTitle(title);
    }

    /**
     * Find Field with expectedName in objectClass. If not found, find first
     * occurrence of target fieldClass in objectClass.
     */
    private Field findField(Class objectClass, Class fieldClass,
                            String expectedName) {
        try {
            final Field field = objectClass.getDeclaredField(expectedName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) { /* Ignore */
        }

        // Search for it if it wasn't found under the expectedName.
        for (final Field field : objectClass.getDeclaredFields()) {
            if (field.getType() == fieldClass) {
                field.setAccessible(true);
                return field;
            }
        }

        return null;
    }

    boolean hasNoDay;

    public void setHasNoDay(boolean bol) {

        hasNoDay = bol;

        if (hasNoDay)

            try {
                /*((ViewGroup) ((ViewGroup) datePicker.getChildAt(0))
                        .getChildAt(0)).getChildAt(2).setVisibility(
                        View.GONE);
*/
                DatePicker dp = this.getDatePicker();
                NumberPicker view0 = (NumberPicker) ((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(0); //获取最前一位的宽度
                NumberPicker view1 = (NumberPicker) ((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(1); //获取中间一位的宽度
                NumberPicker view2 = (NumberPicker) ((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(2);//获取最后一位的宽度


                //年的最大值为2100
                //月的最大值为11
                //日的最大值为28,29,30,31
                int value0 = view0.getMaxValue();//获取第一个View的最大值
                int value1 = view1.getMaxValue();//获取第二个View的最大值
                int value2 = view2.getMaxValue();//获取第三个View的最大值
                if (value0 >= 28 && value0 <= 31) {
                    view0.setVisibility(View.GONE);
                } else if (value1 >= 28 && value1 <= 31) {
                    view1.setVisibility(View.GONE);
                } else if (value2 >= 28 && value2 <= 31) {
                    view2.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public boolean isHasNoDay() {
        return hasNoDay;
    }

}