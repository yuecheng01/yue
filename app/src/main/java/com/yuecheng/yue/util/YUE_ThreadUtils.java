package com.yuecheng.yue.util;

import android.os.Handler;

/**
 * Created by yuecheng on 2017/11/8.
 */

public class YUE_ThreadUtils {

    /**
     * 执行在子线程
     */
    public static void runInThread(Runnable task) {
        new Thread(task).start();
    }
    /**
     *
     * 执行在UI线程
     */
    private static Handler mHandler = new Handler();
    public static void runInUiThread(Runnable task){
        mHandler.post(task);
    }
}
