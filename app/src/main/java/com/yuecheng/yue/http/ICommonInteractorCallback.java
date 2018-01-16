package com.yuecheng.yue.http;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/12/23.
 */

public interface ICommonInteractorCallback {


    void loadSuccess(Object object);
    void loadFailed();
    void loadCompleted();
    //便于对事件的管理,在activity销毁的时候我们要中止事件,给clear掉
    void addDisaposed(Disposable disposable);
}
