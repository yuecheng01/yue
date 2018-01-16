package com.yuecheng.yue.ui.activity.impl;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseActivitySlideBack;
import com.yuecheng.yue.ui.bean.YUE_FriendsBean;
import com.yuecheng.yue.util.YUE_LogUtils;
import com.yuecheng.yue.widget.YUE_NoScrollListView;
import com.yuecheng.yue.widget.selector.YUE_BackResUtils;

import io.rong.imkit.RongIM;

/**
 * Created by administraot on 2017/11/18.
 */

public class YUE_UserDetailActivity extends YUE_BaseActivitySlideBack implements View.OnClickListener {

    private YUE_NoScrollListView mPersonalMomentListView;
    private Button mBtnChat;
    private Button mBtnYuYin;
    private Button mBtnShiPin;

    private YUE_FriendsBean mFriend;
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_userdetail_layout;
    }

    @Override
    protected void initViewsAndEvents() {
        initView();
        initDataEvents();
        initToolBar();
    }
    /**
     * 初始化工具栏
     */
    private void initToolBar() {
        Toolbar mToolBar = findView(R.id.toolbar);
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();

        //使能app bar的导航功能
        ab.setDisplayHomeAsUpEnabled(true);

        ab.setTitle(mFriend.getPhonenum().toString().trim());
        mToolBar.setTitleTextColor(getResources().getColor(R.color.white));
    }


    private void initDataEvents() {
        mFriend = (YUE_FriendsBean) getIntent().getSerializableExtra("friend");

        mBtnChat.setOnClickListener(this);
        mBtnYuYin.setOnClickListener(this);
        mBtnShiPin.setOnClickListener(this);
    }
    private void initView() {
        mPersonalMomentListView = findView(R.id.personal_moment_lv);
        mBtnChat = findView(R.id.personal_chat);
        mBtnYuYin = findView(R.id.personal_yuyin);
        mBtnShiPin = findView(R.id.personal_shipin);
        mBtnChat.setBackground(YUE_BackResUtils.getInstance(mContext).getLoginDrawableSelector());
        mBtnYuYin.setBackground(YUE_BackResUtils.getInstance(mContext).getLoginDrawableSelector());
        mBtnShiPin.setBackground(YUE_BackResUtils.getInstance(mContext).getLoginDrawableSelector());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal_chat:
                String phoneNum = mFriend.getPhonenum();
                String displayName = mFriend.getDisplayname();
                String nickName = mFriend.getNickname();
                YUE_LogUtils.i(TAG,phoneNum+"----"+displayName+"----"+nickName);
                if (!TextUtils.isEmpty(displayName)) {
                    RongIM.getInstance().startPrivateChat(YUE_UserDetailActivity.this,phoneNum ,
                            displayName);
                } else {
                    RongIM.getInstance().startPrivateChat(YUE_UserDetailActivity.this, phoneNum,
                            nickName);
                }
                finish();
                break;
            case R.id.personal_yuyin:

                break;
            case R.id.personal_shipin:

                break;
        }
    }
}
