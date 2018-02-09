package com.yuecheng.yue.ui.activity.impl;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseActivityNoSlideBack;
import com.yuecheng.yue.ui.activity.YUE_IAddFriendView;
import com.yuecheng.yue.ui.bean.SearchedFriendBean;
import com.yuecheng.yue.ui.presenter.YUE_AddFriendViewPresenter;
import com.yuecheng.yue.util.CommonUtils;
import com.yuecheng.yue.util.ImageLoaderUtils;
import com.yuecheng.yue.util.YUE_LogUtils;
import com.yuecheng.yue.util.YUE_ToastUtils;
import com.yuecheng.yue.widget.YUE_CircleImageView;

/**
 * Created by Administrator on 2018/1/25.
 */

public class YUE_AddFriendActivity extends YUE_BaseActivityNoSlideBack implements YUE_IAddFriendView {
    private String TAG = getClass().getSimpleName();
    private EditText mSearchEditText;
    private String mPhoneNum;
    private YUE_AddFriendViewPresenter mPresenter;
    private LinearLayout mSearch_result;
    private TextView mUserNickName;
    private YUE_CircleImageView mUserProtial;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_addfriend_layout;
    }

    @Override
    protected void initViewsAndEvents() {
        Toolbar mToolBar = initToolBars(R.id.toolbar, "添加好友");
        initView();
        initDataEvents();
    }

    private void initView() {
        mSearchEditText = findView(R.id.search_edit);
        mSearch_result = findView(R.id.search_result);
        mUserNickName = findView(R.id.usernickname);
        mUserProtial = findView(R.id.userprotial);
    }

    private void initDataEvents() {
        mPresenter = new YUE_AddFriendViewPresenter(mContext, this);
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 11) {
                    mPhoneNum = charSequence.toString().trim();
                    if (!CommonUtils.isMobileNO(mPhoneNum)) {
                        YUE_ToastUtils.showmessage("非法手机号");
                        return;
                    }
                    CommonUtils.hideKeyboard(YUE_AddFriendActivity.this);
                    mPresenter.queryPersonInfo(mPhoneNum);
                } else {
                    mSearch_result.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void loadResult(final SearchedFriendBean searchedFriendBean) {
        mSearch_result.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(searchedFriendBean.getValue().get(0).getNickname()))
            mUserNickName.setText(searchedFriendBean.getValue().get(0).getNickname());
        if (!TextUtils.isEmpty(searchedFriendBean.getValue().get(0).getPortraituri())) {
            ImageLoaderUtils.displayImageUrl(searchedFriendBean.getValue().get(0).getPortraituri
                    (), mUserProtial, null, null);
        } else {
            mUserProtial.setImageDrawable(getResources().getDrawable(R.drawable.yue));
        }
        mSearch_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPresenter.isFriendOrSelf(mPhoneNum)){
                    YUE_LogUtils.d(TAG, "*****isFriendOrSelf*****");
                    Intent intent =new Intent(YUE_AddFriendActivity.this,YUE_UserDetailActivity
                            .class);
                    intent.putExtra("userId",mPhoneNum);
                    intent.putExtra("userNickName",searchedFriendBean.getValue().get(0).getNickname());
                    startActivity(intent);
                }else {
                    YUE_LogUtils.d(TAG, "*****notFriendOrSelf*****");
                    mPresenter.showAddFriendDialog(mPhoneNum);
                }
            }
        });
    }
}
