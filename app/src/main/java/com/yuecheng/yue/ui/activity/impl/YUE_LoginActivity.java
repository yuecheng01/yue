package com.yuecheng.yue.ui.activity.impl;

import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseActivityNoSlideBack;
import com.yuecheng.yue.ui.activity.YUE_ILoginView;
import com.yuecheng.yue.ui.presenter.YUE_LoginViewPresenter;
import com.yuecheng.yue.util.YUE_AnimationUtil;
import com.yuecheng.yue.widget.circle.YUE_CustomVideoView;
import com.yuecheng.yue.widget.selector.YUE_BackResUtils;

/**
 * Created by yuecheng on 2017/10/29.
 */

public class YUE_LoginActivity extends YUE_BaseActivityNoSlideBack implements YUE_ILoginView,
        View.OnClickListener {

    private LinearLayout mLinearLayout;
    private Button mLogin;
    private Button mRegister;
    private RelativeLayout mRelativeLayout;
    private YUE_LoginViewPresenter mPresenter;
    private YUE_CustomVideoView mYUE_CustomVideoView;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login_layout;
    }

    @Override
    protected void initViewsAndEvents() {
        initViews();
        setViewEvent();
        mPresenter = new YUE_LoginViewPresenter(this, this);
    }

    private void setViewEvent() {
        videoPlay();

        mLogin.setOnClickListener(this);
        mLogin.setBackground(YUE_BackResUtils.getInstance(this).getLoginDrawableSelector());
        mRegister.setOnClickListener(this);
        mRegister.setBackground(YUE_BackResUtils.getInstance(this).getRegDrawableSelector());
    }

    private void videoPlay() {
        mYUE_CustomVideoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+ "/raw/login"));
        mYUE_CustomVideoView.start();
        mYUE_CustomVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }
        });
        mYUE_CustomVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mYUE_CustomVideoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+ "/raw/login"));
                mediaPlayer.start();
            }
        });
    }

    private void initViews() {

        mLogin = findView(R.id.login);
        mRelativeLayout = findView(R.id.rl);
        mRegister = findView(R.id.register);
        mLinearLayout = findView(R.id.randl);
        mYUE_CustomVideoView = findView(R.id.videoplayer);
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
                mPresenter.login();
                break;
            case R.id.register:
                mPresenter.register();
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
    }

    @Override
    public void btnRandLInVisable() {
        mLinearLayout.setVisibility(View.GONE);
        mLinearLayout.setAnimation(YUE_AnimationUtil.moveToViewBottom());
    }

    @Override
    public void jumpToActivity(Class c) {
        startActivity(c);
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
    //返回重启加载
    @Override
    protected void onRestart() {
        videoPlay();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        mYUE_CustomVideoView.resume();
        super.onResume();
    }

    //防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        mYUE_CustomVideoView.stopPlayback();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
