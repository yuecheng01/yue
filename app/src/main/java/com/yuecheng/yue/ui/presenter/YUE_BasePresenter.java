package com.yuecheng.yue.ui.presenter;

import android.os.Handler;
import android.os.Message;

import com.yuecheng.yue.ui.bean.YUE_SPsave;
import com.yuecheng.yue.util.YUE_AppUtils;
import com.yuecheng.yue.util.YUE_SharedPreferencesUtils;

/**
 * Created by yuecheng on 2017/11/6.
 */

public  class YUE_BasePresenter {
    protected String mUserId;
    public YUE_BasePresenter() {
        this.mUserId = (String) YUE_SharedPreferencesUtils.getParam(YUE_AppUtils.getAppContext(), YUE_SPsave.YUE_LOGING_PHONE,"");
    }
}
