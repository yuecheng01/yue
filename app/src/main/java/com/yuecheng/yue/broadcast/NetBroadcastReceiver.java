package com.yuecheng.yue.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.yuecheng.yue.base.YUE_BaseActivityNoSlideBack;
import com.yuecheng.yue.util.CommonUtils;

/**
 * Created by Administrator on 2018/1/31.
 */

public class NetBroadcastReceiver extends BroadcastReceiver {
    public NetEvevt evevt = YUE_BaseActivityNoSlideBack.evevt;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        // 如果相等的话就说明网络状态发生了变化
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = CommonUtils.getNetworkType(context);
            // 接口回调传过去状态的类型
            evevt.onNetChange(netWorkState);
        }
    }

    // 自定义接口
    public static interface NetEvevt {
        public void onNetChange(int netMobile);
    }
}
