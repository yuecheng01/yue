package com.yuecheng.yue.ui.interactor;

import com.yuecheng.yue.http.ICommonInteractorCallback;

import java.util.Map;

/**
 * Created by Administrator on 2018/1/25.
 */

public interface YUE_IAddFriendViewInteractor {
    void getSearchedFriendBean(String mPhoneNum, ICommonInteractorCallback iCommonInteractorCallback);

    void getAddSomeoneFridendBean(Map map, ICommonInteractorCallback iCommonInteractorCallback);
}
