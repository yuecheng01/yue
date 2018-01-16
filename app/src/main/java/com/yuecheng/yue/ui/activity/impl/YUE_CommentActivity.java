package com.yuecheng.yue.ui.activity.impl;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseActivitySlideBack;
import com.yuecheng.yue.ui.activity.YUE_ICommentView;
import com.yuecheng.yue.ui.presenter.YUE_CommentViewPresenter;
import com.yuecheng.yue.util.CommonUtils;
import com.yuecheng.yue.util.YUE_ToastUtils;
import com.yuecheng.yue.util.anim.ViewAnimationUtils;
import com.yuecheng.yue.widget.selector.YUE_BackResUtils;

/**
 * Created by administraot on 2017/12/2.
 */

public class YUE_CommentActivity extends YUE_BaseActivitySlideBack implements YUE_ICommentView ,View.OnClickListener{
    private RecyclerView mCommentList;
    private EditText mCommentWrite;
    private Button mBtnSend;
    private YUE_CommentViewPresenter mPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_comment_layout;
    }

    @Override
    protected void initViewsAndEvents() {
        initToolBar();
        initViews();
        initDataEvents();
    }

    private void initToolBar() {
        Toolbar mToolBar = findView(R.id.toolbar);
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();

        //使能app bar的导航功能
        ab.setDisplayHomeAsUpEnabled(true);

        ab.setTitle("评论列表");
        mToolBar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    private void initViews() {
        mCommentList = findView(R.id.commentlist);
        mCommentWrite = findView(R.id.commentwrite);
        mBtnSend = findView(R.id.bt_send);
        mSwipeRefreshLayout = findView(R.id.commentrefresh);
    }


    private void initDataEvents() {
        mPresenter = new YUE_CommentViewPresenter(this, this);
        mBtnSend.setBackground(YUE_BackResUtils.getInstance(mContext).getLoginDrawableSelector());
        mBtnSend.setOnClickListener(this);

        mCommentList.setLayoutManager(new LinearLayoutManager(this));
        mCommentList.setAdapter(mPresenter.getAdapter());
        //设置Item增加、移除动画
        mCommentList.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        DividerItemDecoration d = new DividerItemDecoration(
                mContext, DividerItemDecoration.VERTICAL);
        d.setDrawable(YUE_BackResUtils.getInstance(mContext).getDividerItemDrawable());
        mCommentList.addItemDecoration(d);

        mSwipeRefreshLayout.setColorSchemeColors(CommonUtils.getColorByAttrId(mContext, R.attr
                .colorPrimary), getResources().getColor(R.color.material_green));
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout
            .OnRefreshListener() {
        @Override
        public void onRefresh() {
//            todo 刷新事件
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_send:
                if (!TextUtils.isEmpty(mCommentWrite.getText())){

                }else {
                    YUE_ToastUtils.getInstance(this).showmessage("什么都没说呢。。。,,ԾㅂԾ,,");
                    ViewAnimationUtils.shake(mBtnSend,false);
                }
                break;
        }
    }
}
