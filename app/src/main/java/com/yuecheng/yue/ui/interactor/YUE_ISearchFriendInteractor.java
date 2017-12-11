package com.yuecheng.yue.ui.interactor;

import android.content.Context;

import com.yuecheng.yue.ui.bean.YUE_ContactsInfo;

import java.util.List;

/**
 * Created by administraot on 2017/12/8.
 */

public interface YUE_ISearchFriendInteractor {
    //获取数据
    List<YUE_ContactsInfo> obtionContacts(Context mContext);
    //根据searchview索引关键字过滤数据
    List<YUE_ContactsInfo> filter(List<YUE_ContactsInfo> yue_contactsInfos, String newText);
}
