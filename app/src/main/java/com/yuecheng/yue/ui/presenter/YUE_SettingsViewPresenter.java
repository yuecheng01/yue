package com.yuecheng.yue.ui.presenter;

import android.content.Context;

import com.yuecheng.yue.ui.activity.YUE_ISettingsView;
import com.yuecheng.yue.ui.activity.impl.YUE_LoginActivity;
import com.yuecheng.yue.ui.interactor.YUE_ISettingsViewInteractor;
import com.yuecheng.yue.ui.interactor.impl.YUE_SettingsViewInteractorImpl;

/**
 * Created by yuecheng on 2017/11/6.
 */

public class YUE_SettingsViewPresenter {
    private Context mContext;
    private YUE_ISettingsView mSettingsView;
    private YUE_ISettingsViewInteractor mInteractor;

    public YUE_SettingsViewPresenter(Context context, YUE_ISettingsView a) {
        this.mContext = context;
        this.mSettingsView = a;
        mInteractor = new YUE_SettingsViewInteractorImpl();
    }

    public void exitUser() {
        mInteractor.clearUserToken();
        mSettingsView.finishAllActivity();
        mSettingsView.jump2Activity(YUE_LoginActivity.class);
    }
}
