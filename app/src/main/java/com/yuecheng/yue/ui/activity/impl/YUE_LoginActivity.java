package com.yuecheng.yue.ui.activity.impl;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseActivity;
import com.yuecheng.yue.ui.activity.YUE_ILoginView;
import com.yuecheng.yue.ui.presenter.YUE_LoginViewPresenter;
import com.yuecheng.yue.util.YUE_AnimationUtil;
import com.yuecheng.yue.util.YUE_LogUtils;
import com.yuecheng.yue.widget.selector.YUE_BackResUtils;

/**
 * Created by yuecheng on 2017/10/29.
 */

public class YUE_LoginActivity extends YUE_BaseActivity implements YUE_ILoginView,
        View.OnClickListener {

    private LinearLayout mLinearLayout;
    private Button mLogin;
    private Button mRegister;
    private RelativeLayout mRelativeLayout;
    private YUE_LoginViewPresenter mYUE_loginViewPresenter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 5:
                    if (null != mYUE_loginViewPresenter)
                        backgroundAlpha((float) msg.obj);
                    break;
            }
        }
    };


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login_layout;
    }

    @Override
    protected void initViewsAndEvents() {
        initViews();
        setViewEvent();
        mYUE_loginViewPresenter = new YUE_LoginViewPresenter(this, this);
    }

    private void setViewEvent() {
        mLogin.setOnClickListener(this);
        mLogin.setBackground(YUE_BackResUtils.getInstance(this).getLoginDrawableSelector());
        mRegister.setOnClickListener(this);
        mRegister.setBackground(YUE_BackResUtils.getInstance(this).getRegDrawableSelector());
    }

    private void initViews() {

        mLogin = findView(R.id.login);
        mRelativeLayout = findView(R.id.rl);
        mRegister = findView(R.id.register);
        mLinearLayout = findView(R.id.randl);
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                mYUE_loginViewPresenter.login();
                break;
            case R.id.register:
                mYUE_loginViewPresenter.register();
                break;
        }
    }

    @Override
    public View getViewParent() {
        return mRelativeLayout;
    }

    @Override
    public void btnRandLVisable() {
        mLinearLayout.setVisibility(View.VISIBLE);
        mLinearLayout.setAnimation(YUE_AnimationUtil.moveToViewLocation());
        new Thread(mYUE_loginViewPresenter.getAperanceTask()).start();
    }

    @Override
    public void btnRandLInVisable() {
        mLinearLayout.setVisibility(View.INVISIBLE);
        mLinearLayout.setAnimation(YUE_AnimationUtil.moveToViewBottom());
        new Thread(mYUE_loginViewPresenter.getDissmissTask()).start();
    }

    @Override
    public void jumpToActivity(Class c) {
        startActivity(c);
    }


    @Override
    public Handler getHandler() {
        return mHandler != null ? mHandler : null;
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    @Override
    public void finishActivity() {
        this.finish();
    }

    @Override
    public void showMessage(String s) {
        ShowMessage(s);
    }

    ;
}
