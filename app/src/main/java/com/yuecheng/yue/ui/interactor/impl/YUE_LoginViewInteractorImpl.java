package com.yuecheng.yue.ui.interactor.impl;

import android.os.Handler;

import com.yuecheng.yue.http.ApiServicesManager;
import com.yuecheng.yue.http.ICommonInteractorCallback;
import com.yuecheng.yue.ui.bean.YUE_LoginBean;
import com.yuecheng.yue.ui.bean.YUE_SPsave;
import com.yuecheng.yue.ui.interactor.YUE_ILoginViewInteractor;
import com.yuecheng.yue.util.YUE_AppUtils;
import com.yuecheng.yue.util.YUE_LogUtils;
import com.yuecheng.yue.util.YUE_SharedPreferencesUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yuecheng on 2017/11/5.
 */

public class YUE_LoginViewInteractorImpl implements YUE_ILoginViewInteractor {
    public YUE_LoginViewInteractorImpl() {
        super();
    }


    @Override
    public void getLoginRequestData(String userId , String password, final ICommonInteractorCallback l) {
        YUE_LogUtils.d("userId--->","YUE_LoginViewInteractorImpl-->"+userId);
        YUE_LogUtils.d("password--->","YUE_LoginViewInteractorImpl-->"+password);
        ApiServicesManager.getInstence().getYueapi()
                .getYUE_LoginBean(userId,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<YUE_LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        l.addDisaposed(d);
                    }

                    @Override
                    public void onNext(YUE_LoginBean yue_loginBean) {
                        YUE_LogUtils.d("yue_loginBean",yue_loginBean.getResuletcode());
                        l.loadSuccess(yue_loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        l.loadFailed();
                    }

                    @Override
                    public void onComplete() {
                        l.loadCompleted();
                    }
                });
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

    @Override
    public void saveRememberPwd() {
        YUE_SharedPreferencesUtils.setParam(YUE_AppUtils.getAppContext(),
                YUE_SPsave.REMEMBER_PWD,
                "1");
    }

    @Override
    public void cancleRememberPwd() {
        YUE_SharedPreferencesUtils.setParam(YUE_AppUtils.getAppContext(),
                YUE_SPsave.REMEMBER_PWD,
                "-1");
    }

    @Override
    public String getUserName() {
        return (String) YUE_SharedPreferencesUtils.getParam(YUE_AppUtils.getAppContext(),
                YUE_SPsave.YUE_LOGING_PHONE, "");
    }

    @Override
    public String getUserPassword() {
        return (String) YUE_SharedPreferencesUtils.getParam(YUE_AppUtils.getAppContext(),
                YUE_SPsave.YUE_LOGING_PASSWORD, "");
    }

    @Override
    public String getIsRemPwd() {
        return (String) YUE_SharedPreferencesUtils.getParam(YUE_AppUtils.getAppContext(),
                YUE_SPsave.REMEMBER_PWD, "");
    }
}
