package com.yuecheng.yue.util;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yuecheng.yue.R;

import static android.graphics.Color.BLACK;

/**
 * Created by yuecheng on 2017/10/30.
 */

public class YUE_ToastUtils extends Toast {
    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    private static YUE_ToastUtils mInstance = null;
    private static TextView tvToast;
    private static Activity mContext;
    private YUE_ToastUtils(Activity context) {
        super(context);
        this.mContext =context;
        tvToast = (TextView) LayoutInflater.from(context).inflate(R.layout.widget_toast_layout, null);
//        tvToast = new TextView(mContext);
//        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,Gravity.CENTER);
//        tvToast.setLayoutParams(parms);
//        tvToast.setTextSize(12);
//        tvToast.setTextColor(Color.WHITE);
//        tvToast.setBackgroundColor(Color.BLACK);
    }
    public static YUE_ToastUtils getInstance(Activity a){
        if (null == mInstance){
            mInstance = new YUE_ToastUtils(a);
        }
        return mInstance;
    }
    public void showmessage(final Object message){
        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvToast.setText(message + "");
                mInstance.setGravity(Gravity.CENTER, 0, CommonUtils.getScreenHeight(mContext)/3);
                mInstance.setDuration(Toast.LENGTH_SHORT);
                mInstance.setView(tvToast);
                mInstance.show();
            }
        });
    }
}
