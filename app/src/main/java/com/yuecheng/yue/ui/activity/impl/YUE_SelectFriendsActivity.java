package com.yuecheng.yue.ui.activity.impl;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseActivitySlideBack;

/**
 * Created by administraot on 2017/12/17.
 */

public class YUE_SelectFriendsActivity extends YUE_BaseActivitySlideBack {
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_selectfriends_layout;
    }
    @Override
    protected void initViewsAndEvents() {
        initToolBar();
    }

    private void initToolBar() {
        Toolbar mToolBar = findView(R.id.toolbar);
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();

        //使能app bar的导航功能
        ab.setDisplayHomeAsUpEnabled(true);

        ab.setTitle("选择群组成员");
        mToolBar.setTitleTextColor(getResources().getColor(R.color.white));
    }
}
