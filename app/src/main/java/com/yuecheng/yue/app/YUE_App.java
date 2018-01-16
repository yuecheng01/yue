package com.yuecheng.yue.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;

import com.yuecheng.yue.db.DBManager;
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
        mContext = getApplicationContext();
        YUE_LogUtils.setDeBug(true);//设置是否日志打印
        YUE_AppUtils.init(this);
        RongIM.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
