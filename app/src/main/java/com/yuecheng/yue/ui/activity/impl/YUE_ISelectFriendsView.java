package com.yuecheng.yue.ui.activity.impl;

import com.yuecheng.yue.db.entity.Friend;

import java.util.List;

/**
 * Created by Administrator on 2018/1/28.
 */

public interface YUE_ISelectFriendsView {
    void updateAdapter(List<Friend> friendList);
}
