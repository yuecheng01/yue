package com.yuecheng.yue.ui.activity;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;

import com.yuecheng.yue.ui.activity.impl.YUE_CommentActivity;

/**
 * Created by administraot on 2017/11/29.
 */

public interface YUE_IFriendCircleView {
    FragmentManager getSupportFManager();

    void jump2Activity(Class a );

    void showZan();
}
