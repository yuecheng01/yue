package com.yuecheng.yue.ui.activity.impl;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseActivity;
import com.yuecheng.yue.ui.activity.YUE_IFriendCircleView;
import com.yuecheng.yue.ui.presenter.YUE_FriendCircleViewPresenter;
import com.yuecheng.yue.util.CommonUtils;
import com.yuecheng.yue.util.YUE_LogUtils;
import com.yuecheng.yue.widget.picloadlib.PhotoPickActivity;

/**
 * Created by administraot on 2017/11/25.
 */

public class YUE_FriendCircleActivity extends YUE_BaseActivity implements YUE_IFriendCircleView,
        View.OnClickListener {
    private FloatingActionButton mSendMoment;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private YUE_FriendCircleViewPresenter mPresenter;



    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_friendcircle_layout;
    }

    @Override
    protected void initViewsAndEvents() {
        mPresenter = new YUE_FriendCircleViewPresenter(this, this);
        initToolBar();
        initViews();
        initDataEvents();
    }

    /**
     * 功能操作
     */
    private void initDataEvents() {
        mSendMoment.setOnClickListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(CommonUtils.getColorByAttrId(mContext, R.attr
                .colorPrimary), getResources().getColor(R.color.material_green));
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mPresenter.getAdapter());
        mPresenter.getAdapter().setOnItemClickListener(mPresenter.getListener());
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        mSendMoment = findView(R.id.actionbar);
        mSwipeRefreshLayout = findView(R.id.swiperefresh);
        mRecyclerView = findView(R.id.recycler);
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

        ab.setTitle("朋友圈");
        mToolBar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.actionbar:
                startActivity(PhotoPickActivity.class);
                break;
        }
    }

    /**
     * 刷新事件监听
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout
            .OnRefreshListener() {
        @Override
        public void onRefresh() {
//            todo 刷新事件
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    /**
     * 返回按钮监听
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public FragmentManager getSupportFManager() {
        return getSupportFragmentManager();
    }

    @Override
    public void jump2Activity(Class a) {
        startActivity(a);
        overridePendingTransition(R.anim.activity_translate_in, R.anim
                .activity_translate_out);
    }

    /**
     * 点赞
     */
    @Override
    public void showZan() {
        YUE_LogUtils.e("zan---->","Liked it !");
        CoordinatorLayout mCoordinatorLayout = findView(R.id.CoordinatorLayout);
        showSnackBar(mCoordinatorLayout,"Liked it !");
    }
}
