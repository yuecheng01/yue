package com.yuecheng.yue.ui.presenter;

import android.content.Context;

import com.yuecheng.yue.ui.activity.YUE_ISettingsView;
import com.yuecheng.yue.ui.activity.impl.YUE_LoginActivity;
import com.yuecheng.yue.ui.bean.YUE_SPsave;
import com.yuecheng.yue.ui.interactor.YUE_ISettingsViewInteractor;
import com.yuecheng.yue.ui.interactor.impl.YUE_SettingsViewInteractorImpl;
import com.yuecheng.yue.util.YUE_AppUtils;
import com.yuecheng.yue.util.YUE_SharedPreferencesUtils;

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
        String mIsRememberPwd = (String) YUE_SharedPreferencesUtils.getParam(YUE_AppUtils.getAppContext(), YUE_SPsave.REMEMBER_PWD,"");
        mInteractor.clearUserToken();
       switch (mIsRememberPwd){
           case "1":
               break;
           case "-1":
//               YUE_SharedPreferencesUtils.setParam(YUE_AppUtils.getAppContext(),
//                       YUE_SPsave.YUE_LOGING_PHONE,
//                       "");
               YUE_SharedPreferencesUtils.setParam(YUE_AppUtils.getAppContext(),
                       YUE_SPsave.YUE_LOGING_PASSWORD,
                       "");
               break;
       }

        mSettingsView.finishAllActivity();
        mSettingsView.jump2Activity(YUE_LoginActivity.class);
    }
}
