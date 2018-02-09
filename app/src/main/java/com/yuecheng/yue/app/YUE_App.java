package com.yuecheng.yue.app;

import android.content.Context;
import com.squareup.leakcanary.LeakCanary;
import com.yuecheng.yue.util.YUE_AppUtils;
import com.yuecheng.yue.util.YUE_LogUtils;

import io.rong.imkit.RongIM;

/**
 * Created by yuecheng on 2017/10/29.
 */

public class YUE_App extends MultiDexApplication{
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
//        LeakCanary.install(this);//检测内存泄漏
        YUE_LogUtils.setDeBug(true);//设置是否日志打印
        YUE_AppUtils.init(this);
        RongIM.init(this);
        YUE_UserInfoManager.init(mContext);
    }
}
