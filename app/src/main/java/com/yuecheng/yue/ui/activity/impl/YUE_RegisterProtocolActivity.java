package com.yuecheng.yue.ui.activity.impl;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseActivitySlideBack;

/**
 * Created by administraot on 2017/12/15.
 */

public class YUE_RegisterProtocolActivity extends YUE_BaseActivitySlideBack {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_registerprotocol_layout;
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

        ab.setTitle("yue用户协议");
        mToolBar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YUE_RegisterProtocolActivity.this.finish();
            }
        });
    }

}
