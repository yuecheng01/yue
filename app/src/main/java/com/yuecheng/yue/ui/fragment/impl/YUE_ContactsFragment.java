package com.yuecheng.yue.ui.fragment.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseLazyFragment;
import com.yuecheng.yue.http.pinyin.CharacterParser;
import com.yuecheng.yue.http.pinyin.PinyinComparator;
import com.yuecheng.yue.ui.activity.impl.YUE_UserDetailActivity;
import com.yuecheng.yue.ui.adapter.YUE_FridendListAdapter;
import com.yuecheng.yue.ui.bean.YUE_FriendsBean;
import com.yuecheng.yue.ui.fragment.YUE_IContactsFragment;
import com.yuecheng.yue.ui.presenter.YUE_ContactsFragmentPresenter;
import com.yuecheng.yue.util.CommonUtils;
import com.yuecheng.yue.widget.LoadDialog;
import com.yuecheng.yue.widget.SideBar;
import com.yuecheng.yue.widget.YUE_CircleImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 联系人页
 * Created by yuecheng on 2017/11/6.
 */

public class YUE_ContactsFragment extends YUE_BaseLazyFragment implements YUE_IContactsFragment, View.OnClickListener {
    private YUE_ContactsFragmentPresenter mPresenter;
    private ListView mListView;
    private TextView mNoFriends;
    private View mHeadView;
    private YUE_CircleImageView mSelectableRoundedImageView;
    private TextView mUnreadTextView;
    private TextView mNameTextView;
    private YUE_FridendListAdapter mAdapter;
    private LayoutInflater mLayoutInflater;
    /**
     * 中部展示的字母提示
     */
    private TextView mDialogTextView;
    private SideBar mSidBar;
    private List<YUE_FriendsBean> mFriendList;
    private List<YUE_FriendsBean> mFilteredFriendList;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser mCharacterParser;
    private PinyinComparator mPinyinComparator;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        mLayoutInflater = LayoutInflater.from(mActivity);
        View view = mLayoutInflater.inflate(R.layout.fragment_contacts_layout, null);
        setContentView(view);
        initViews(view);
        initData();
        mPresenter.getDataUpdateContact();
    }


    private void initData() {
        mPresenter = new YUE_ContactsFragmentPresenter(mActivity, this);
//        设置 展示在好友列表头部的个人信息，
        mPresenter.updatePersonalUI();
        mFriendList = new ArrayList<>();
        mFilteredFriendList = new ArrayList<>();
        YUE_FridendListAdapter adapter = new YUE_FridendListAdapter(mActivity, mFriendList);
        mListView.setAdapter(adapter);

        //实例化汉字转拼音类
        mCharacterParser = CharacterParser.getInstance();
        mPinyinComparator = PinyinComparator.getInstance();
    }

    private void initViews(View view) {
        mListView = (ListView) view.findViewById(R.id.friendlistview);
        mNoFriends = (TextView) view.findViewById(R.id.show_no_friend);
        mDialogTextView = (TextView) view.findViewById(R.id.group_dialog);
        mSidBar = (SideBar) view.findViewById(R.id.sidrbar);

        mHeadView = mLayoutInflater.inflate(R.layout.item_contact_list_header, null);
        mUnreadTextView = (TextView) mHeadView.findViewById(R.id.tv_unread);
        RelativeLayout newFriendsLayout = (RelativeLayout) mHeadView.findViewById(R.id.re_newfriends);
        RelativeLayout groupLayout = (RelativeLayout) mHeadView.findViewById(R.id.re_chatroom);
        RelativeLayout publicServiceLayout = (RelativeLayout) mHeadView.findViewById(R.id.publicservice);
        RelativeLayout selfLayout = (RelativeLayout) mHeadView.findViewById(R.id.contact_me_item);
        mSelectableRoundedImageView = (YUE_CircleImageView) mHeadView.findViewById(R.id.contact_me_img);
        mNameTextView = (TextView) mHeadView.findViewById(R.id.contact_me_name);

        mListView.addHeaderView(mHeadView);
        mNoFriends.setVisibility(View.VISIBLE);

        newFriendsLayout.setOnClickListener(this);
        groupLayout.setOnClickListener(this);
        publicServiceLayout.setOnClickListener(this);
        selfLayout.setOnClickListener(this);

        //设置右侧触摸监听
        mSidBar.setTextView(mDialogTextView);
        mSidBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mListView.setSelection(position+1);
                }
            }
        });
    }


    @Override
    public void loadFriendsList(List<YUE_FriendsBean> list) {
        boolean isReloadList = false;
        if (mFriendList != null && mFriendList.size() > 0) {
            mFriendList.clear();
            isReloadList = true;
        }
        mFriendList = list;
        if (mFriendList != null && mFriendList.size() > 0) {
            mPresenter.handleFriendDataForSort();
            mNoFriends.setVisibility(View.GONE);
        } else {
            mNoFriends.setVisibility(View.VISIBLE);
        }
        // 根据a-z进行排序源数据
        Collections.sort(mFriendList, mPinyinComparator);
        if (isReloadList) {
            mSidBar.setVisibility(View.VISIBLE);
            mAdapter.updateListView(mFriendList);
        } else {
            mSidBar.setVisibility(View.VISIBLE);
            mAdapter = new YUE_FridendListAdapter(mActivity, mFriendList);
            mListView.setAdapter(mAdapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (mListView.getHeaderViewsCount() > 0) {
                        startFriendDetailsPage(mFriendList.get(i - 1).getPhonenum(),mFriendList
                                .get(i-1).getNickname());
                    } else {
//                        startFriendDetailsPage(mFilteredFriendList.get(position));
                    }
                }
            });

            mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    YUE_FriendsBean bean = mFriendList.get(position - 1);
//                    startFriendDetailsPage(bean);
                    return true;
                }
            });
        }
    }

    private void startFriendDetailsPage(String userId,String nickName) {
        Intent intent = new Intent(getActivity(), YUE_UserDetailActivity.class);
//        intent.putExtra("type", CLICK_CONTACT_FRAGMENT_FRIEND);
        intent.putExtra("userId", userId);
        intent.putExtra("nickName", nickName);
        startActivity(intent);
    }

    @Override
    public void setpersonalUI(String mId, String mCacheName) {
        mSelectableRoundedImageView.setImageDrawable(getResources().getDrawable(R.drawable.default_chatroom));
        mNameTextView.setText(mCacheName);
    }

    @Override
    public List<YUE_FriendsBean> getFriendList() {
        return mFriendList;
    }

    @Override
    public void dismissLoadingDialog() {
        LoadDialog.dismiss(getActivity());
    }

    @Override
    public void showLoadingDialog() {
        LoadDialog.show(getActivity());
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.re_newfriends:

                break;
            case R.id.re_chatroom:

                break;
            case R.id.publicservice:

                break;
            case R.id.contact_me_item:

                break;
        }
    }
}
