package com.yuecheng.yue.ui.activity.impl;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseActivity;
import com.yuecheng.yue.base.YUE_BaseAppManager;
import com.yuecheng.yue.ui.activity.YUE_ISettingsView;
import com.yuecheng.yue.ui.presenter.YUE_SettingsViewPresenter;
import com.yuecheng.yue.widget.selector.YUE_BackResUtils;

/**
 * Created by yuecheng on 2017/11/6.
 */

public class YUE_SettingsActivity extends YUE_BaseActivity implements View.OnClickListener,
        YUE_ISettingsView {
    private YUE_SettingsViewPresenter mPresenter;
    private LinearLayout mSetUserInfo;
    private Button mExit;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_settings_layout;
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

        ab.setTitle("设置");
        mToolBar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    private void initDataEvents() {
        mPresenter = new YUE_SettingsViewPresenter(this, this);
        mExit.setBackground(YUE_BackResUtils.getInstance(mContext).getLoginDrawableSelector());
        mExit.setOnClickListener(this);
        mSetUserInfo.setOnClickListener(this);
    }

    private void initViews() {
        mSetUserInfo = findView(R.id.setuserinfo);
        mExit = findView(R.id.exit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit:
                mPresenter.exitUser();
                break;
            case R.id.setuserinfo:
               startActivity(YUE_SetUserInfoActivity.class);
                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void jump2Activity(Class<YUE_LoginActivity> yue_loginActivityClass) {
        startActivity(yue_loginActivityClass);
    }

    @Override
    public void finishAllActivity() {
        YUE_BaseAppManager.getInstance().clear();
    }
}
