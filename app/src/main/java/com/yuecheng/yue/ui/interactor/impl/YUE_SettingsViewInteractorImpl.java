package com.yuecheng.yue.ui.interactor.impl;

import com.yuecheng.yue.ui.bean.YUE_SPsave;
import com.yuecheng.yue.ui.interactor.YUE_ISettingsViewInteractor;
import com.yuecheng.yue.util.YUE_AppUtils;
import com.yuecheng.yue.util.YUE_SharedPreferencesUtils;

/**
 * Created by yuecheng on 2017/11/6.
 */

public class YUE_SettingsViewInteractorImpl implements YUE_ISettingsViewInteractor {
    @Override
    public void clearUserToken() {
        YUE_SharedPreferencesUtils.setParam(YUE_AppUtils.getAppContext(),YUE_SPsave.YUE_TOKEN,"");
    }
}
