package com.yuecheng.yue.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.yuecheng.yue.R;

/**
 * Created by Administrator on 2018/1/22.
 */

public class ToastUtil {
    private static Toast toast;
    private static View view;

    private ToastUtil() {
    }

    private static void getToast(Context context) {
        if (toast == null) {
            toast = new Toast(context);
        }
        if (view == null) {
//            view = Toast.makeText(context, "", Toast.LENGTH_SHORT).getView();
            view = LayoutInflater.from(context).inflate(R.layout.widget_toast_layout, null);
        }
        toast.setView(view);
    }

    public static void showShortToast(Context context,String tag, CharSequence msg) {
        showToast(context.getApplicationContext(),tag, msg, Toast.LENGTH_SHORT);
    }

    public static void showShortToast(Context context,String tag, int resId) {
        showToast(context.getApplicationContext(),tag, resId, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(Context context,String tag, CharSequence msg) {
        showToast(context.getApplicationContext(),tag, msg, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(Context context,String tag, int resId) {
        showToast(context.getApplicationContext(),tag, resId, Toast.LENGTH_LONG);
    }

    private static void showToast(Context context, String tag,CharSequence msg, int duration) {
        try {
            getToast(context);
            toast.setText(msg);
            toast.setDuration(duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } catch (Exception e) {
            YUE_LogUtils.e(tag,e.getMessage());
        }
    }

    private static void showToast(Context context,String tag, int resId, int duration) {
        try {
            if (resId == 0) {
                return;
            }
            getToast(context);
            toast.setText(resId);
            toast.setDuration(duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } catch (Exception e) {
            YUE_LogUtils.e(tag,e.getMessage());
        }
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
