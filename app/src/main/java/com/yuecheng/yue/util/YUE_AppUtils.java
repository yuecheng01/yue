package com.yuecheng.yue.util;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import com.yuecheng.yue.ui.bean.YUE_SPsave;

/**
 * Created by yuecheng on 2017/10/29.
 */

public class YUE_AppUtils {
    private static Context mContext;
    private static Thread mUiThread;

    public static void init(Context context) {
        mContext = context;
        mUiThread = Thread.currentThread();
    }

    public static boolean isUIThread() {
        return Thread.currentThread() == mUiThread;
    }

    public static Resources getResource() {
        return mContext.getResources();
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static boolean checkIsLogin() {
        String token = (String) YUE_SharedPreferencesUtils.getParam(mContext,
                YUE_SPsave.YUE_TOKEN,""
                );
        String nickName = (String) YUE_SharedPreferencesUtils.getParam(mContext,
                YUE_SPsave.YUE_LOGING_PHONE,
                "");
        String passWord = (String) YUE_SharedPreferencesUtils.getParam(mContext,
                YUE_SPsave.YUE_LOGING_PHONE,
                "");
        return (!TextUtils.isEmpty(token));
    }
}
