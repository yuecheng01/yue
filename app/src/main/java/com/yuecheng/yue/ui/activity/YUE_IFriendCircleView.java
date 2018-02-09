package com.yuecheng.yue.ui.activity;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;

import com.yuecheng.yue.ui.activity.impl.YUE_CommentActivity;
import com.yuecheng.yue.ui.bean.CircleItem;
import com.yuecheng.yue.ui.bean.CommentConfig;
import com.yuecheng.yue.ui.bean.CommentItem;
import com.yuecheng.yue.ui.bean.FavortItem;

import java.util.List;

/**
 * Created by administraot on 2017/11/29.
 */

public interface YUE_IFriendCircleView {
    FragmentManager getSupportFManager();
    void update2loadData(int loadType, List<CircleItem> datas);

    void update2DeleteCircle(String circleId);

    void update2DeleteComment(int circlePosition, String commentItemId);

    void updateEditTextBodyVisible(int visible, CommentConfig commentConfig);

    void update2AddFavorite(int circlePosition, FavortItem item);

    void update2DeleteFavort(int circlePosition, String favortId);

    void update2AddComment(int circlePosition, CommentItem newItem);
}
