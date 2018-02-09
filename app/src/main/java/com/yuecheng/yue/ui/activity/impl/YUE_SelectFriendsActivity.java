package com.yuecheng.yue.ui.activity.impl;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.yuecheng.yue.R;
import com.yuecheng.yue.app.YUE_UserInfoManager;
import com.yuecheng.yue.base.YUE_BaseActivityNoSlideBack;
import com.yuecheng.yue.db.entity.Friend;
import com.yuecheng.yue.http.ICommonInteractorCallback;
import com.yuecheng.yue.ui.adapter.SelectFriendAdapter;
import com.yuecheng.yue.ui.presenter.YUE_SelectFriendsPresenter;
import com.yuecheng.yue.widget.SideBar;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by administraot on 2017/12/17.
 */

public class YUE_SelectFriendsActivity extends YUE_BaseActivityNoSlideBack implements YUE_ISelectFriendsView{
    private boolean mIsAddGroupMember;
    private boolean mIsCrateGroup;
    private RecyclerView mRecycler;
    private YUE_SelectFriendsPresenter mPresenter;
    private SelectFriendAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    /**
     * 中部展示的字母提示
     */
    private TextView dialog;
    private SideBar mSideBar;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_selectfriends_layout;
    }

    @Override
    protected void initViewsAndEvents() {
        //接收intent传过来的值,
        mPresenter = new YUE_SelectFriendsPresenter(mContext,this);
        getIntentData();
        initToolBar();
        initView();
        initDataEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confir,menu);


        return super.onCreateOptionsMenu(menu);
    }

    private void initDataEvents() {
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mLinearLayoutManager.scrollToPositionWithOffset(position,0);
                }
            }
        });
        mAdapter = new SelectFriendAdapter();
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLinearLayoutManager);
        mRecycler.setAdapter(mAdapter);
        //todo do somethisng
        if (false){

        }else {
           mPresenter.setContactsList();
        }
    }

    private void initView() {
        dialog = (TextView) findViewById(R.id.dis_dialog);
        mSideBar = (SideBar) findViewById(R.id.dis_sidrbar);
        mSideBar.setTextView(dialog);
        mRecycler = findView(R.id.dis_friendrv);
    }

    private void getIntentData() {
        mIsAddGroupMember = getIntent().getBooleanExtra("isAddGroupMember", false);
        mIsCrateGroup = getIntent().getBooleanExtra("createGroup", false);
    }
    Toolbar mToolbar;
    private void initToolBar() {
        if (mIsAddGroupMember) {
            mToolbar=  initToolBars(R.id.toolbar, "添加群组成员");
        } else if (mIsCrateGroup) {
            mToolbar= initToolBars(R.id.toolbar, "选择群组成员");
        } else {
            mToolbar= initToolBars(R.id.toolbar, "选择联系人");
        }
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_confir:

                        break;
                }
                return true;
            }
        });
    }

    //更新列表数据,填充好友信息数据
    @Override
    public void updateAdapter(List<Friend> friendList) {
        mAdapter.setDatas(friendList);
        mAdapter.notifyDataSetChanged();
    }
}
