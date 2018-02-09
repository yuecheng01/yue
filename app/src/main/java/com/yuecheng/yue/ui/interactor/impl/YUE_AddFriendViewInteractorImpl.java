package com.yuecheng.yue.ui.interactor.impl;

import com.yuecheng.yue.http.ApiServicesManager;
import com.yuecheng.yue.http.ICommonInteractorCallback;
import com.yuecheng.yue.ui.bean.AddFridendBean;
import com.yuecheng.yue.ui.bean.SearchedFriendBean;
import com.yuecheng.yue.ui.interactor.YUE_IAddFriendViewInteractor;
import com.yuecheng.yue.util.YUE_LogUtils;
import com.yuecheng.yue.util.YUE_ToastUtils;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/1/25.
 */

public class YUE_AddFriendViewInteractorImpl implements YUE_IAddFriendViewInteractor {
    public YUE_AddFriendViewInteractorImpl() {
        super();
    }

    @Override
    public void getSearchedFriendBean(String mPhoneNum, final ICommonInteractorCallback l) {
        ApiServicesManager.getInstence().getYueapi()
                .getSearchedFriendBean(mPhoneNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchedFriendBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        l.addDisaposed(d);
                    }

                    @Override
                    public void onNext(SearchedFriendBean searchedFriendBean) {
                        l.loadSuccess(searchedFriendBean);
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
    public void getAddSomeoneFridendBean(Map hashMap, final ICommonInteractorCallback l) {
        ApiServicesManager.getInstence().getYueapi()
                .getAddSomeoneFridendBean(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddFridendBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        l.addDisaposed(d);
                    }

                    @Override
                    public void onNext(AddFridendBean addFridendBean) {
                        l.loadSuccess(addFridendBean);
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
