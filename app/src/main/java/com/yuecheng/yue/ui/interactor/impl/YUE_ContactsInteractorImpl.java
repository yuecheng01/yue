package com.yuecheng.yue.ui.interactor.impl;

import android.os.Handler;

import com.yuecheng.yue.http.ApiServicesManager;
import com.yuecheng.yue.http.ICommonInteractorCallback;
import com.yuecheng.yue.ui.bean.YUE_FriendsListBean;
import com.yuecheng.yue.ui.interactor.YUE_IContactsInteractor;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yuecheng on 2017/11/12.
 */

public class YUE_ContactsInteractorImpl implements YUE_IContactsInteractor{
    public YUE_ContactsInteractorImpl() {
        super();
    }

}
