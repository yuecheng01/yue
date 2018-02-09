package com.yuecheng.yue.ui.activity.impl;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseActivitySlideBack;
import com.yuecheng.yue.ui.activity.YUE_ISetUserInfoView;
import com.yuecheng.yue.ui.presenter.YUE_SetUserInfoViewPresenter;

/**
 * Created by administraot on 2017/11/22.
 */

public class YUE_SetUserInfoActivity extends YUE_BaseActivitySlideBack implements YUE_ISetUserInfoView, View.OnClickListener{
    private LinearLayout mSetUserIcon;
    private LinearLayout mSetUserSex;
    private LinearLayout mSetUserDisplay;
    private LinearLayout mSetuserBirtyday;
    private LinearLayout mSetuserAddress;
    private LinearLayout mSetuserHomeAddress;
    private YUE_SetUserInfoViewPresenter mPresenter;
    private TextView mUserSex;
    private TextView mUserDisplay;
    private TextView mUserBirthday;
    private TextView mUserAddressSelect;
    private TextView mHomeAddress;
    private ImageView mHeadIcon;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_setuserinfo_layout;
    }
    @Override
    protected void initViewsAndEvents() {
        initToolBar();
        initViews();
        initDataEvents();
    }

    private void initToolBar() {
        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();

        //使能app bar的导航功能
        ab.setDisplayHomeAsUpEnabled(true);

        ab.setTitle("编辑资料");
        mToolBar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_finish:
                        mPresenter.submitUpdateInfo();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_finish,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initDataEvents() {
        mPresenter = new YUE_SetUserInfoViewPresenter(this,this);
        mSetUserIcon.setOnClickListener(this);
        mSetUserSex.setOnClickListener(this);
        mSetUserDisplay.setOnClickListener(this);
        //  处理图像选择后回调监听
        mPresenter.pickIconListener();
        mSetuserBirtyday.setOnClickListener(this);
        mSetuserAddress.setOnClickListener(this);
        mSetuserHomeAddress.setOnClickListener(this);
    }

    private void initViews() {
        mSetUserIcon = findView(R.id.setusericon);
        mSetUserSex = findView(R.id.setusersex);
        mUserSex=findView(R.id.usersex);
        mUserDisplay=findView(R.id.userdisplay);
        mSetUserDisplay=findView(R.id.setuserdisplay);
        mSetuserBirtyday=findView(R.id.setuserbirtyday);
        mUserBirthday=findView(R.id.userbirthday);
        mSetuserAddress=findView(R.id.setuseraddress);
        mUserAddressSelect=findView(R.id.address_select);
        mSetuserHomeAddress=findView(R.id.setuserhomeaddress);
        mHomeAddress=findView(R.id.homeaddress);
        mHeadIcon = findView(R.id.head_icon);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setusericon:
                //todo 设置头像
                mPresenter.setUserIcon();
                break;
            case R.id.setuserdisplay:
                //todo 设置签名
                mPresenter.setUserDisplay();
                break;
            case R.id.setusersex:
                //todo 设置性别
                mPresenter.setUserSex();
                break;
            case R.id.setuserbirtyday:
                //todo 设置生日
                mPresenter.setUserBirtyday();
                break;
            case R.id.setuseraddress:
                //todo 设置地址
                mPresenter.setUserAddress();
                break;
            case R.id.setuserhomeaddress:
                //todo 设置地址
                mPresenter.setUserHomeAddress();
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.dealResult(requestCode,resultCode,data);
    }

    @Override
    public void setSex(String mSexName) {
        mUserSex.setText(mSexName);
    }

    @Override
    public View getWrappedView() {
        return mSetUserDisplay;
    }

    @Override
    public void setUserSign(String s) {
        mUserDisplay.setText(s);
    }

    @Override
    public String getUserSign() {
        return  mUserDisplay.getText().toString();
    }

    @Override
    public void setBirthday(String dateDesc) {
        mUserBirthday.setText(dateDesc);
    }

    @Override
    public void setNowAddress(String addressSelect) {
        mUserAddressSelect.setText(addressSelect);
    }

    @Override
    public void setHomeTownAddress(String homeAdressSelect) {
        mHomeAddress.setText(homeAdressSelect);
    }

    @Override
    public void setIcon(Uri uri) {
        mHeadIcon.setImageURI(uri);
    }


}
