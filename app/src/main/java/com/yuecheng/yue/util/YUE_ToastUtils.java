package com.yuecheng.yue.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.yuecheng.yue.R;

/**
 * Created by yuecheng on 2017/10/30.
 */

public class YUE_ToastUtils {
    private static TextView tvToast;
    private static Toast mToast;

    private YUE_ToastUtils() {
    }

    private static void getToast(Context context) {
        if (mToast == null) {
            mToast = new Toast(context);
        }
        if (tvToast == null) {
            tvToast = (TextView) LayoutInflater.from(context).inflate(R.layout.widget_toast_layout, null);
        }
        mToast.setView(tvToast);
    }

    public static void showmessage( final Object message) {
        getToast(YUE_AppUtils.getAppContext());
        tvToast.setText(message + "");
        //居下
//                mInstance.setGravity(Gravity.CENTER, 0, CommonUtils.getScreenHeight(mContext) / 3);
        //居中
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(tvToast);
        mToast.show();

    }
}
