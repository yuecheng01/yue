package com.yuecheng.yue.ui.activity.impl;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseActivity;
import com.yuecheng.yue.ui.activity.YUE_ISearchFriendView;
import com.yuecheng.yue.ui.adapter.YUE_PhoneContractAdapter;
import com.yuecheng.yue.ui.presenter.YUE_SearchFriendViewPresenter;
import com.yuecheng.yue.util.CommonUtils;
import com.yuecheng.yue.widget.selector.YUE_BackResUtils;

/**
 * Created by yuecheng on 2017/11/8.
 */

public class YUE_SearchFriendActivity extends YUE_BaseActivity implements YUE_ISearchFriendView {
    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private YUE_SearchFriendViewPresenter mPresenter;
    private YUE_PhoneContractAdapter mAdapter;

    @Override
    protected void initViewsAndEvents() {
        initToolBar();
        initViews();
        initDataEvents();
    }

    private void initDataEvents() {
        mPresenter = new YUE_SearchFriendViewPresenter(this,this);
        mSearchView.setSubmitButtonEnabled(false);
        // 设置该SearchView默认是否自动缩小为图标
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setQueryHint("请输入关键字");
        CommonUtils.setCursorIcon(mSearchView);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            //申请权限  第二个参数是一个 数组 说明可以同时申请多个权限
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS}, 1);
        } else {//已授权
            setRecyclerView();
        }
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                mAdapter.setFilter( mPresenter.getFilter(newText));
                return true;
            }
        });
    }

    private void setRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = mPresenter.getAdapter();
        mRecyclerView.setAdapter(mAdapter);
        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        DividerItemDecoration d = new DividerItemDecoration(
                mContext, DividerItemDecoration.VERTICAL);
        d.setDrawable(YUE_BackResUtils.getInstance(mContext).getDividerItemDrawable());
        mRecyclerView.addItemDecoration(d);
    }

    private void initToolBar() {
        Toolbar mToolBar = findView(R.id.toolbar);
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();

        //使能app bar的导航功能
        ab.setDisplayHomeAsUpEnabled(true);

        ab.setTitle("添加好友");
        mToolBar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    private void initViews() {
        mSearchView = findView(R.id.searchView);
        mRecyclerView = findView(R.id.recyclerView);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setRecyclerView();
            } else {
                ShowMessage("获取联系人的权限申请失败");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_searfriend_layout;
    }
}
