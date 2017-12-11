package com.yuecheng.yue.ui.interactor.impl;

import android.os.Handler;

import com.yuecheng.yue.ui.bean.YUE_SPsave;
import com.yuecheng.yue.ui.interactor.YUE_ILoginViewInteractor;
import com.yuecheng.yue.util.YUE_AppUtils;
import com.yuecheng.yue.util.YUE_SharedPreferencesUtils;

/**
 * Created by yuecheng on 2017/11/5.
 */

public class YUE_LoginViewInteractorImpl extends
        YUE_BaseInteractor implements YUE_ILoginViewInteractor {
    public YUE_LoginViewInteractorImpl() {
        super();
    }

    @Override
    public void getLoginRequestData(String url,
                                    String key1, String key2,
                                    String param1, String param2,
                                    int what, String tag, Handler h) {
        super.getData(url,
                key1, key2, param1, param2,
                what, tag, h);
    }

    @Override
    public void saveNandP(String phoneString, String passwordString) {
        YUE_SharedPreferencesUtils.setParam(YUE_AppUtils.getAppContext(),
                YUE_SPsave.YUE_LOGING_PHONE,
                phoneString);
        YUE_SharedPreferencesUtils.setParam(YUE_AppUtils.getAppContext(),
                YUE_SPsave.YUE_LOGING_PASSWORD,
                passwordString);
    }

    @Override
    public void saveRongToken(String token) {
        YUE_SharedPreferencesUtils.setParam(YUE_AppUtils.getAppContext(),
                YUE_SPsave.YUE_TOKEN,
                token);
    }
}
