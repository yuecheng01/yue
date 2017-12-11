package com.yuecheng.yue.ui.activity.impl;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseActivity;
import com.yuecheng.yue.ui.bean.YUE_FriendsBean;
import com.yuecheng.yue.util.YUE_LogUtils;
import com.yuecheng.yue.widget.YUE_NoScrollListView;
import com.yuecheng.yue.widget.selector.YUE_BackResUtils;

import io.rong.imkit.RongIM;

/**
 * Created by administraot on 2017/11/18.
 */

public class YUE_UserDetailActivity extends YUE_BaseActivity implements View.OnClickListener {
    // 控制ToolBar的变量
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;

    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    private ImageView mBigPic;//大图
//    private LinearLayout mLlTitleContainer; // Title的LinearLayout
//    private FrameLayout mFlTitleContainer; // Title的FrameLayout
    private AppBarLayout mAppBar; // 整个可以滑动的AppBar
    private TextView mTvToolbarTitle; // 标题栏Title
    private Toolbar mTbToolbar; // 工具栏
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
    }


    private void initDataEvents() {
        mFriend = (YUE_FriendsBean) getIntent().getSerializableExtra("friend");
//        mTbToolbar.setTitle("");
       /* // 向上或向下滑动AppBar就会触发监听
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxScroll = appBarLayout.getTotalScrollRange();
                float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
//                handleAlphaOnTitle(percentage);
//                handleToolbarTitleVisibility(percentage);
            }
        });*/
//        initParallaxValues(); // 设置自动滑动（视差）效果
        mBtnChat.setOnClickListener(this);
        mBtnYuYin.setOnClickListener(this);
        mBtnShiPin.setOnClickListener(this);
    }

    // 设置自动滑动（视差）效果
    private void initParallaxValues() {
        CollapsingToolbarLayout.LayoutParams petDetailsLp =
                (CollapsingToolbarLayout.LayoutParams) mBigPic.getLayoutParams();

//        CollapsingToolbarLayout.LayoutParams petBackgroundLp =
//                (CollapsingToolbarLayout.LayoutParams) mFlTitleContainer.getLayoutParams();

        petDetailsLp.setParallaxMultiplier(0.9f);
//        petBackgroundLp.setParallaxMultiplier(0.3f);

        mBigPic.setLayoutParams(petDetailsLp);
//        mFlTitleContainer.setLayoutParams(petBackgroundLp);
    }

    // 处理ToolBar的显示
    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTvToolbarTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }
        } else {
            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTvToolbarTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    // 控制Title的显示
//    private void handleAlphaOnTitle(float percentage) {
//        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
//            if (mIsTheTitleContainerVisible) {
//                startAlphaAnimation(mLlTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
//                mIsTheTitleContainerVisible = false;
//            }
//        } else {
//            if (!mIsTheTitleContainerVisible) {
//                startAlphaAnimation(mLlTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
//                mIsTheTitleContainerVisible = true;
//            }
//        }
//    }

    // 设置渐变的动画
    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    private void initView() {
        mBigPic = findView(R.id.main_iv_placeholder);
//        mLlTitleContainer = findView(R.id.main_ll_title_container);
//        mFlTitleContainer = findView(R.id.main_fl_title);
        mAppBar = findView(R.id.main_app_bar);
        mTvToolbarTitle = findView(R.id.main_tv_toolbar_title);
//        mTbToolbar = findView(R.id.main_tb_toolbar);
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
