package com.yuecheng.yue.ui.presenter;

import android.content.Context;

import com.yuecheng.yue.ui.activity.YUE_ISearchFriendView;
import com.yuecheng.yue.ui.adapter.YUE_PhoneContractAdapter;
import com.yuecheng.yue.ui.bean.YUE_ContactsInfo;
import com.yuecheng.yue.ui.interactor.YUE_ISearchFriendInteractor;
import com.yuecheng.yue.ui.interactor.impl.YUE_SearchFriendInteractorImpl;

import java.util.List;

/**
 * Created by administraot on 2017/12/8.
 */

public class YUE_SearchFriendViewPresenter {
    private Context mContext;
    private YUE_ISearchFriendView mView;
    private YUE_ISearchFriendInteractor mInteractor;
    private List<YUE_ContactsInfo> list;

    public YUE_SearchFriendViewPresenter(Context context, YUE_ISearchFriendView view) {
        this.mContext = context;
        this.mView = view;
        mInteractor = new YUE_SearchFriendInteractorImpl();
        list = mInteractor.obtionContacts(mContext);
    }

    public YUE_PhoneContractAdapter getAdapter() {
        return new YUE_PhoneContractAdapter(mInteractor.obtionContacts(mContext));
    }

    public List<YUE_ContactsInfo> getFilter(String newText) {
        return mInteractor.filter(list, newText);
    }
}
