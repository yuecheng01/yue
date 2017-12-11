package com.yuecheng.yue.app;

import android.app.Application;
import android.content.Context;
import com.yuecheng.yue.util.YUE_AppUtils;
import com.yuecheng.yue.util.YUE_LogUtils;
import io.rong.imkit.RongIM;

/**
 * Created by yuecheng on 2017/10/29.
 */

public class YUE_App extends Application{
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        YUE_LogUtils.setDeBug(true);//设置是否日志打印
        YUE_AppUtils.init(this);
        RongIM.init(this);
    }

    public static Context getInstance() {
        return mContext;
    }
}
