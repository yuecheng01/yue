package com.yuecheng.yue.ui.activity.impl;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseActivitySlideBack;
import com.yuecheng.yue.ui.activity.YUE_IFriendCircleView;
import com.yuecheng.yue.ui.adapter.DivItemDecoration;
import com.yuecheng.yue.ui.adapter.YUE_CircleAdapter;
import com.yuecheng.yue.ui.bean.CircleItem;
import com.yuecheng.yue.ui.bean.CommentConfig;
import com.yuecheng.yue.ui.bean.CommentItem;
import com.yuecheng.yue.ui.bean.FavortItem;
import com.yuecheng.yue.ui.presenter.YUE_FriendCircleViewPresenter;
import com.yuecheng.yue.util.CommonUtils;
import com.yuecheng.yue.util.YUE_LogUtils;
import com.yuecheng.yue.util.YUE_ToastUtils;
import com.yuecheng.yue.util.anim.ViewAnimationUtils;
import com.yuecheng.yue.widget.EmojiEditText.EmojiEditextView;
import com.yuecheng.yue.widget.circle.CommentListView;
import com.yuecheng.yue.widget.picloadlib.PhotoPickActivity;
import com.yuecheng.yue.widget.selector.YUE_BackResUtils;

import java.util.List;

import io.rong.imageloader.core.ImageLoader;

/**
 * Created by administraot on 2017/11/25.
 */

public class YUE_FriendCircleActivity extends YUE_BaseActivitySlideBack implements YUE_IFriendCircleView,
        View.OnClickListener {
    private String TAG = getClass().getSimpleName();
    private SuperRecyclerView mRecyclerView;
    private YUE_FriendCircleViewPresenter mPresenter;
    private final static int TYPE_PULLREFRESH = 1;
    private final static int TYPE_UPLOADREFRESH = 2;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private LinearLayout edittextbody;
    private CommentConfig commentConfig;
    private LinearLayoutManager layoutManager;
    private int selectCircleItemH;
    private int selectCommentItemOffset;
    private EmojiEditextView mEmojiEditextView;
    private RelativeLayout bodyLayout;
    private int currentKeyboardH;
    private int screenHeight;
    private int editTextBodyHeight;
    private Toolbar mToolBar;

    private YUE_CircleAdapter mAdapter;
    private int mLastPosition = 0;

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

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt("currentPosition", mRecyclerView.getVerticalScrollbarPosition());
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_publish, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 功能操作
     */
    private void initDataEvents() {
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        //添加分割线
        DividerItemDecoration d = new DividerItemDecoration(
                mContext, DividerItemDecoration.VERTICAL);
        d.setDrawable(YUE_BackResUtils.getInstance(mContext).getDividerItemDrawable());
        mRecyclerView.addItemDecoration(d);
//        mRecyclerView.addItemDecoration( new DivItemDecoration(2, true));
        mRecyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (edittextbody.getVisibility() == View.VISIBLE) {
                    updateEditTextBodyVisible(View.GONE, null);
                    return true;
                }
                return false;
            }
        });
        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.loadData(TYPE_PULLREFRESH);
                    }
                }, 2000);
            }
        };
        //设置刷新事件
        mRecyclerView.setRefreshListener(refreshListener);
        //设置滚动监听,滚动过程中停止加载.停止滚动后开始加载
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    ImageLoader.getInstance().resume();
//                    Glide.with(MainActivity.this).resumeRequests();
                } else {
                    ImageLoader.getInstance().pause();
//                    Glide.with(MainActivity.this).pauseRequests();
                }

            }
        });
        //设置适配器
        mAdapter = mPresenter.getAdapter();
        mRecyclerView.setAdapter(mAdapter);
        //加载数据
        mPresenter.loadData(TYPE_PULLREFRESH);
        mEmojiEditextView.addOnSendClickListener(new EmojiEditextView.OnSendClickListener() {
            @Override
            public void click() {
                //发布评论
                String content = mEmojiEditextView.getTextValue().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    YUE_ToastUtils.getInstance((Activity) mContext).showmessage("什么都没说呢。。。,,ԾㅂԾ,,");
                    ViewAnimationUtils.shake(mEmojiEditextView.getEditext(),true);
                    return;
                }
                mPresenter.addComment(content, commentConfig);
                updateEditTextBodyVisible(View.GONE, null);
            }
        });
        setViewTreeObserver();
    }

    private void setViewTreeObserver() {
        bodyLayout = (RelativeLayout) findViewById(R.id.bodyLayout);
        final ViewTreeObserver swipeRefreshLayoutVTO = bodyLayout.getViewTreeObserver();
        swipeRefreshLayoutVTO.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                bodyLayout.getWindowVisibleDisplayFrame(r);
                int statusBarH = getStatusBarHeight();//状态栏高度
                int screenH = bodyLayout.getRootView().getHeight();
                if (r.top != statusBarH) {
                    //在这个demo中r.top代表的是状态栏高度，在沉浸式状态栏时r.top＝0，通过getStatusBarHeight获取状态栏高度
                    r.top = statusBarH;
                }
                int keyboardH = screenH - (r.bottom - r.top);
                YUE_LogUtils.d(TAG, "screenH＝ " + screenH + " &keyboardH = " + keyboardH + " &r.bottom="
                        + r
                        .bottom + " &top=" + r.top + " &statusBarH=" + statusBarH);

                if (keyboardH == currentKeyboardH) {//有变化时才处理，否则会陷入死循环
                    return;
                }

                currentKeyboardH = keyboardH;
                screenHeight = screenH;//应用屏幕的高度
                editTextBodyHeight = edittextbody.getHeight();

                if (keyboardH < 150) {//说明是隐藏键盘的情况
//                    updateEditTextBodyVisible(View.GONE, null);
                    return;
                }
                //偏移listview
                if (layoutManager != null && commentConfig != null) {
                    layoutManager.scrollToPositionWithOffset(commentConfig.circlePosition +
                            mAdapter
                                    .HEADVIEW_SIZE, getListviewOffset(commentConfig));
                }
            }
        });

    }

    private int getListviewOffset(CommentConfig commentConfig) {
        if (commentConfig == null)
            return 0;
        //这里如果你的listview上面还有其它占高度的控件，则需要减去该控件高度，listview的headview除外。
        //int listviewOffset = mScreenHeight - mSelectCircleItemH - mCurrentKeyboardH - mEditTextBodyHeight;
        int listviewOffset = screenHeight - selectCircleItemH - currentKeyboardH -
                editTextBodyHeight - mToolBar.getHeight();
        if (commentConfig.commentType == CommentConfig.Type.REPLY) {
            //回复评论的情况
            listviewOffset = listviewOffset + selectCommentItemOffset;
        }
        YUE_LogUtils.i(TAG, "listviewOffset : " + listviewOffset);
        return listviewOffset;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        mRecyclerView = findView(R.id.recycler);
        edittextbody = (LinearLayout) findViewById(R.id.editTextBodyLl);
        mEmojiEditextView = (EmojiEditextView)findView(R.id.emojiedit);
    }

    /**
     * 初始化工具栏
     */
    private void initToolBar() {
        mToolBar = findView(R.id.toolbar);
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();

        //使能app bar的导航功能
        if (ab != null) {
            ab.setDisplayShowTitleEnabled(false);//不使用系统的title
            ab.setDisplayHomeAsUpEnabled(true);
        }
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_send:
                        startActivity(PhotoPickActivity.class);
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (edittextbody != null && edittextbody.getVisibility() == View.VISIBLE) {
                //edittextbody.setVisibility(View.GONE);
                updateEditTextBodyVisible(View.GONE, null);
                return true;
            }else {
                startActivity(YUE_HomeActivity.class);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

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
        YUE_LogUtils.e("zan---->", "Liked it !");
        CoordinatorLayout mCoordinatorLayout = findView(R.id.CoordinatorLayout);
        showSnackBar(mCoordinatorLayout, "Liked it !");
    }

    @Override
    public void update2loadData(int loadType, List<CircleItem> datas) {
        if (loadType == TYPE_PULLREFRESH) {
            mRecyclerView.setRefreshing(false);
            mAdapter.setDatas(datas);
        } else if (loadType == TYPE_UPLOADREFRESH) {
            mAdapter.getDatas().addAll(datas);
        }
        mAdapter.notifyDataSetChanged();
        if (mAdapter.getDatas().size() < 45 + mAdapter.HEADVIEW_SIZE) {
            mRecyclerView.setupMoreListener(new OnMoreListener() {
                @Override
                public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPresenter.loadData(TYPE_UPLOADREFRESH);
                        }
                    }, 2000);

                }
            }, 1);
        } else {
            mRecyclerView.removeMoreListener();
            mRecyclerView.hideMoreProgress();
        }
    }

    @Override
    public void update2DeleteCircle(String circleId) {
        List<CircleItem> circleItems = mAdapter.getDatas();
        for (int i = 0; i < circleItems.size(); i++) {
            if (circleId.equals(circleItems.get(i).getId())) {
                circleItems.remove(i);
                mAdapter.notifyDataSetChanged();
                //circleAdapter.notifyItemRemoved(i+1);
                return;
            }
        }
    }

    @Override
    public void update2DeleteComment(int circlePosition, String commentItemId) {
        CircleItem item = (CircleItem) mAdapter.getDatas().get(circlePosition);
        List<CommentItem> items = item.getComments();
        for (int i = 0; i < items.size(); i++) {
            if (commentItemId.equals(items.get(i).getId())) {
                items.remove(i);
                mAdapter.notifyDataSetChanged();
                //circleAdapter.notifyItemChanged(circlePosition+1);
                return;
            }
        }
    }

    //弹出评论框
    @Override
    public void updateEditTextBodyVisible(int visible, CommentConfig commentConfig) {
        this.commentConfig = commentConfig;
        edittextbody.setVisibility(visible);

        measureCircleItemHighAndCommentItemOffset(commentConfig);

        if (View.VISIBLE == visible) {
            mEmojiEditextView.requestEditTextFocus();
            mEmojiEditextView.gridViewGone();
            //弹出键盘
            CommonUtils.showSoftInput(mEmojiEditextView.getContext(), mEmojiEditextView);

        } else if (View.GONE == visible) {
            //隐藏键盘
            CommonUtils.hideSoftInput(mEmojiEditextView.getContext(), mEmojiEditextView);
        }
    }

    private void measureCircleItemHighAndCommentItemOffset(CommentConfig commentConfig) {
        if (commentConfig == null)
            return;

        int firstPosition = layoutManager.findFirstVisibleItemPosition();
        //只能返回当前可见区域（列表可滚动）的子项
        View selectCircleItem = layoutManager.getChildAt(commentConfig.circlePosition +
                YUE_CircleAdapter.HEADVIEW_SIZE - firstPosition);

        if (selectCircleItem != null) {
            selectCircleItemH = selectCircleItem.getHeight();
        }

        if (commentConfig.commentType == CommentConfig.Type.REPLY) {
            //回复评论的情况
            CommentListView commentLv = (CommentListView) selectCircleItem.findViewById(R.id.commentList);
            if (commentLv != null) {
                //找到要回复的评论view,计算出该view距离所属动态底部的距离
                View selectCommentItem = commentLv.getChildAt(commentConfig.commentPosition);
                if (selectCommentItem != null) {
                    //选择的commentItem距选择的CircleItem底部的距离
                    selectCommentItemOffset = 0;
                    View parentView = selectCommentItem;
                    do {
                        int subItemBottom = parentView.getBottom();
                        parentView = (View) parentView.getParent();
                        if (parentView != null) {
                            selectCommentItemOffset += (parentView.getHeight() - subItemBottom);
                        }
                    } while (parentView != null && parentView != selectCircleItem);
                }
            }
        }
    }

    @Override
    public void update2AddFavorite(int circlePosition, FavortItem addItem) {
        if (addItem != null) {
            CircleItem item = (CircleItem) mAdapter.getDatas().get(circlePosition);
            item.getFavorters().add(addItem);
            mAdapter.notifyDataSetChanged();
            //circleAdapter.notifyItemChanged(circlePosition+1);
        }
    }

    @Override
    public void update2DeleteFavort(int circlePosition, String favortId) {
        CircleItem item = (CircleItem) mAdapter.getDatas().get(circlePosition);
        List<FavortItem> items = item.getFavorters();
        for (int i = 0; i < items.size(); i++) {
            if (favortId.equals(items.get(i).getId())) {
                items.remove(i);
                mAdapter.notifyDataSetChanged();
                //circleAdapter.notifyItemChanged(circlePosition+1);
                return;
            }
        }
    }

    @Override
    public void update2AddComment(int circlePosition, CommentItem addItem) {
        if (addItem != null) {
            CircleItem item = (CircleItem) mAdapter.getDatas().get(circlePosition);
            item.getComments().add(addItem);
            mAdapter.notifyDataSetChanged();
            //circleAdapter.notifyItemChanged(circlePosition+1);
        }
        //清空评论文本
        mEmojiEditextView.setTextValue("");
    }
}
