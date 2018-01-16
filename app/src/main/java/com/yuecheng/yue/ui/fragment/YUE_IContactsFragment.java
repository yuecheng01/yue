package com.yuecheng.yue.ui.fragment;

import com.yuecheng.yue.ui.bean.YUE_FriendsBean;

import java.util.List;

/**
 * Created by yuecheng on 2017/11/11.
 */

public interface YUE_IContactsFragment {

    void loadFriendsList(List<YUE_FriendsBean> list);

    void setpersonalUI(String mId, String mCacheName);

    List<YUE_FriendsBean> getFriendList();

    void setRefreshCancle();
}
