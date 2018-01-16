package com.yuecheng.yue.ui.activity.impl;

import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;
import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseActivitySlideBack;
import com.yuecheng.yue.util.YUE_AnimationUtil;
import com.yuecheng.yue.util.YUE_AppUtils;
import com.yuecheng.yue.util.YUE_ThreadUtils;

/**
 * Created by yuecheng on 2017/11/7.
 */

public class YUE_SplashActivity extends YUE_BaseActivitySlideBack {
    private TextView mYue;
    @Override
    protected void initViewsAndEvents() {
        mYue = findView(R.id.tv_splash);
        mYue.setVisibility(View.VISIBLE);
        mYue.setAnimation(YUE_AnimationUtil.moveToViewLocation());
        if (YUE_AppUtils.checkIsLogin()) {
//        RongIM.connect(YUE_SharedPreferencesUtils.getParam(this, YUE_SPsave.YUE_TOKEN,""),SealAppContext.getInstance().getConnectCallback());
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
