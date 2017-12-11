package com.yuecheng.yue.ui.interactor.impl;

import android.os.Handler;

import com.yuecheng.yue.ui.interactor.YUE_IContactsInteractor;

/**
 * Created by yuecheng on 2017/11/12.
 */

public class YUE_ContactsInteractorImpl extends YUE_BaseInteractor implements YUE_IContactsInteractor{
    public YUE_ContactsInteractorImpl() {
        super();
    }

    @Override
    public void getFriendsList(String getfriendslist,
                               String usetId1, String statues, String userId, String s,
                               int i, String tag, Handler handler) {
        super.getData(getfriendslist,
                usetId1,statues,userId,s,
                i,tag,handler);
    }
}
