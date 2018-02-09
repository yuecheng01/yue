package com.yuecheng.yue.ui.activity.impl;

import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;
import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseActivitySlideBack;
import com.yuecheng.yue.ui.bean.YUE_SPsave;
import com.yuecheng.yue.util.YUE_AnimationUtil;
import com.yuecheng.yue.util.YUE_AppUtils;
import com.yuecheng.yue.util.YUE_LogUtils;
import com.yuecheng.yue.util.YUE_SharedPreferencesUtils;
import com.yuecheng.yue.util.YUE_ThreadUtils;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by yuecheng on 2017/11/7.
 */

public class YUE_SplashActivity extends YUE_BaseActivitySlideBack {
    private String TAG = getClass().getSimpleName();
    private TextView mYue;
    @Override
    protected void initViewsAndEvents() {
        mYue = findView(R.id.tv_splash);
        mYue.setVisibility(View.VISIBLE);
        mYue.setAnimation(YUE_AnimationUtil.moveToViewLocation());
        if (YUE_AppUtils.checkIsLogin()) {
        RongIM.connect((String) YUE_SharedPreferencesUtils.getParam(this, YUE_SPsave.YUE_TOKEN,""),new
                RongIMClient.ConnectCallback(){
                    @Override
                    public void onSuccess(String s) {
                    YUE_LogUtils.d(TAG, "ConnectCallback connect onSuccess");
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        YUE_LogUtils.d(TAG, "ConnectCallback connect onError");
                    }

                    @Override
                    public void onTokenIncorrect() {
                        YUE_LogUtils.d(TAG, "ConnectCallback connect onTokenIncorrect");
                    }
                });
            YUE_ThreadUtils.runInThread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(2000);
                    goToMain();
                    finish();
                }
            });
        }else {
            YUE_ThreadUtils.runInThread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(2000);
                    goToLogin();
                    finish();
                }
            });
        }
    }

    private void goToLogin() {
        startActivity(YUE_LoginActivity.class);
        finish();
    }

    private void goToMain() {
        startActivity(YUE_HomeActivity.class);
        finish();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash_layout;
    }
}
