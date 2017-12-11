package com.yuecheng.yue.ui.activity;

import com.yuecheng.yue.ui.activity.impl.YUE_LoginActivity;

/**
 * Created by yuecheng on 2017/11/6.
 */

public interface YUE_ISettingsView {
    void jump2Activity(Class<YUE_LoginActivity> yue_loginActivityClass);

    void finishAllActivity();
}
