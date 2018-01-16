package com.yuecheng.yue.ui.interactor;

import android.os.Handler;

import com.yuecheng.yue.http.ICommonInteractorCallback;

/**
 * Created by yuecheng on 2017/11/5.
 */

public interface YUE_ILoginViewInteractor {
    void getLoginRequestData(String userId,String password, ICommonInteractorCallback l);
    void saveNandP(String phoneString, String passwordString);

    void saveRongToken(String token);

    void saveRememberPwd();

    void cancleRememberPwd();

    String getUserName();

    String getUserPassword();

    String getIsRemPwd();
}
