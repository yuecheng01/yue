package com.yuecheng.yue.ui.interactor.impl;

import android.os.Handler;

import com.yuecheng.yue.http.ApiServicesManager;
import com.yuecheng.yue.http.ICommonInteractorCallback;
import com.yuecheng.yue.ui.bean.IsRegisterOrNotBean;
import com.yuecheng.yue.ui.bean.MessageCodeBean;
import com.yuecheng.yue.ui.bean.RegisterBean;
import com.yuecheng.yue.ui.bean.ResetPasswordBean;
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
    public void getLoginRequestData(String userId, String password, final ICommonInteractorCallback l) {
        YUE_LogUtils.d("userId--->", "YUE_LoginViewInteractorImpl-->" + userId);
        YUE_LogUtils.d("password--->", "YUE_LoginViewInteractorImpl-->" + password);
        ApiServicesManager.getInstence().getYueapi()
                .getYUE_LoginBean(userId, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<YUE_LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        l.addDisaposed(d);
                    }

                    @Override
                    public void onNext(YUE_LoginBean yue_loginBean) {
                        YUE_LogUtils.d("yue_loginBean", yue_loginBean.getResuletcode());
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

    //提交注册信息到服务器.
    @Override
    public void submitRegisterInfo(String regPhoneNum, String nickName, String
            regPassword, String regPasswordConfir, final ICommonInteractorCallback l) {
        ApiServicesManager.getInstence().getYueapi()
                .getRegisterBean(regPhoneNum, nickName, regPassword, regPasswordConfir)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        l.addDisaposed(d);
                    }

                    @Override
                    public void onNext(RegisterBean registerBean) {
                        l.loadSuccess(registerBean);
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

    //短信验证码验证
    @Override
    public void confirMessageCode(String s, final ICommonInteractorCallback l) {
        ApiServicesManager.getInstence().getYueapi()
                .getMessageCodeBean(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MessageCodeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        l.addDisaposed(d);
                    }

                    @Override
                    public void onNext(MessageCodeBean messageCodeBean) {
                        l.loadSuccess(messageCodeBean);
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

    /**
     * 返回regPhoneNum账号是否是已注册账号;
     *
     * @param regPhoneNum 手机号
     * @param l           回调
     */
    @Override
    public void getIsRegisterOrNotBean(String regPhoneNum, final ICommonInteractorCallback l) {
        ApiServicesManager.getInstence().getYueapi()
                .getIsRegisterOrNotBean(regPhoneNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IsRegisterOrNotBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        l.addDisaposed(d);
                    }

                    @Override
                    public void onNext(IsRegisterOrNotBean isRegisterOrNotBean) {
                        l.loadSuccess(isRegisterOrNotBean);
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

    /**
     * 重置密码
     *
     * @param mNewPasswordStr
     * @param mNewPasswordFirStr
     * @param l
     */
    @Override
    public void getResetPasswordBean(String resetPwdUserId, String mNewPasswordStr, String
            mNewPasswordFirStr, final ICommonInteractorCallback l) {
        ApiServicesManager.getInstence().getYueapi()
                .getResetPasswordBean(resetPwdUserId, mNewPasswordStr, mNewPasswordFirStr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResetPasswordBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        l.addDisaposed(d);
                    }

                    @Override
                    public void onNext(ResetPasswordBean resetPasswordBean) {
                        l.loadSuccess(resetPasswordBean);
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
}
