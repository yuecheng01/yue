package com.yuecheng.yue.ui.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.alibaba.fastjson.JSON;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.yuecheng.yue.R;
import com.yuecheng.yue.http.YUE_API;
import com.yuecheng.yue.ui.activity.YUE_ILoginView;
import com.yuecheng.yue.ui.activity.impl.YUE_HomeActivity;
import com.yuecheng.yue.ui.activity.impl.YUE_LoginActivity;
import com.yuecheng.yue.ui.bean.YUE_LoginBean;
import com.yuecheng.yue.ui.interactor.YUE_ILoginViewInteractor;
import com.yuecheng.yue.ui.interactor.impl.YUE_LoginViewInteractorImpl;
import com.yuecheng.yue.util.YUE_LogUtils;
import com.yuecheng.yue.widget.YUE_CustomPopWindow;
import com.yuecheng.yue.widget.selector.YUE_BackResUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by yuecheng on 2017/10/29.
 */

public class YUE_LoginViewPresenter {
    private YUE_ILoginView mYUE_iLoginView;
    private YUE_ILoginViewInteractor mInteractor;
    private NormalDialog mNormalDialog;
    private Context mContext;
    private PopupWindow.OnDismissListener mOnTouchListener;
    private YUE_CustomPopWindow mYUE_customPopWindow;
    private Handler mHandler;
    private float alpha = 1.0f;
    private TextInputEditText mUserName;
    private TextInputEditText mPassWord;
    private String mUsername;
    private String mUserPassword;
    private YUE_LoginBean mYUE_loginBean;
    private Handler mLoginBeanHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mYUE_loginBean = JSON.parseObject((String) msg.getData().get("loginbean"),
                            YUE_LoginBean.class);
//                        mYUE_loginBean = (YUE_LoginBean) msg.getData().getSerializable
// ("loginbean");
                    YUE_LogUtils.i("mYUE_LoginBean----->", mYUE_loginBean.toString());
                    switch (mYUE_loginBean.getResuletcode()) {
                        case 0:
                            if (null != mYUE_iLoginView)
                                mYUE_iLoginView.showMessage(mContext.getResources().getString(R
                                        .string.faillogin));
                            break;
                        case 1:
                            //成功登陆
                            if (null != mYUE_loginBean && null != mInteractor) {
                                YUE_LogUtils.i("token---->", "token---->" + mYUE_loginBean
                                        .getValue().getToken());
                                mInteractor.saveNandP(mUsername, mUserPassword);
                                if (null != mYUE_loginBean.getValue() &&
                                        (!mYUE_loginBean.getValue().getToken().equals(""))) {
                                    mInteractor.saveRongToken(mYUE_loginBean.getValue().getToken());
                                    mYUE_customPopWindow.dissmiss();
                                    mYUE_iLoginView.finishActivity();
                                    mYUE_iLoginView.jumpToActivity(YUE_HomeActivity.class);
                                }
                            }
                            break;
                    }

                    break;
            }
        }
    };

    public YUE_LoginViewPresenter(Context c, YUE_LoginActivity a) {
        super();
        this.mYUE_iLoginView = a;
        this.mContext = c;
        mInteractor = new YUE_LoginViewInteractorImpl();
        this.mHandler = mYUE_iLoginView.getHandler();
        mOnTouchListener = new MyTouchListener();
    }

    /**
     * 登录页注册按钮功能
     */
    public void register() {
        mYUE_iLoginView.btnRandLInVisable();
        mNormalDialog = new NormalDialog(mContext).isTitleShow(false)
                .content(mContext.getResources().getString(R.string.registerinfo))
                .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                .btnNum(1).btnText(mContext.getResources().getString(R.string.infr));
        mNormalDialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                mNormalDialog.dismiss();
                mYUE_iLoginView.btnRandLVisable();
            }
        });
        mNormalDialog.show();
        mNormalDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mYUE_iLoginView.btnRandLVisable();
            }
        });
    }

    /**
     * 登录页登录按钮功能
     */
    public void login() {
        mYUE_iLoginView.btnRandLInVisable();
        View mContentView = LayoutInflater.from(mContext).inflate(R.layout.logindialog_layout,
                null);
        handleContentView(mContentView);
        mYUE_customPopWindow = new YUE_CustomPopWindow.PopWindowBuilder(mContext)
                .setView(mContentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.PopupAnimation)
                .setFocusable(true)
                .setTouchable(true)
                .setBackGround(new ColorDrawable(0))
                .setOutsideTouchable(true)
                .setOnDissmissListener(mOnTouchListener)
                .create()
                .showAtLocation(mYUE_iLoginView.getViewParent(), Gravity.CENTER, 0, 0);
        mUserName = (TextInputEditText) mContentView.findViewById(R.id.username);
        mPassWord = (TextInputEditText) mContentView.findViewById(R.id.password);
        final TextInputLayout mUserNameLayout = (TextInputLayout) mContentView.findViewById(R.id
                .usernamecontainer);
        mUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 16) {
                    mUserNameLayout.setError("请最多输入16个字符");
                } else {
                    mUserNameLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void handleContentView(View mContentView) {
        Button mLogin = (Button) mContentView.findViewById(R.id.login);
        mLogin.setBackground(YUE_BackResUtils.getInstance(mContext).getLoginDrawableSelector());
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsername = mUserName.getText().toString().trim();
                mUserPassword = mPassWord.getText().toString().trim();
                if (null != mUsername && null != mPassWord && "" != mUsername && "" !=
                        mUserPassword) {
                    mInteractor.getLoginRequestData(YUE_API.LOGIN,
                            "userId", "password", mUsername, mUserPassword,
                            1, "loginbean", mLoginBeanHandler);
                } else {
                    mYUE_iLoginView.showMessage(mContext.getResources().getString(R.string
                            .nullpoint));
                }
            }
        });
    }

    public Runnable getAperanceTask() {
        return AperanceTask;
    }

    public Runnable getDissmissTask() {
        return DismissTask;
    }

    class MyTouchListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
//            mYUE_customPopWindow.dissmiss();
            mYUE_iLoginView.btnRandLVisable();
        }
    }

    /**
     * 显现效果
     */
    Runnable AperanceTask = new Runnable() {
        @Override
        public void run() {
            //此处while的条件alpha不能<= 否则会出现黑屏
            while (alpha < 1f) {
                try {
                    Thread.sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                YUE_LogUtils.d("HeadPortrait", "alpha:" + alpha);
                Message msg = mHandler.obtainMessage();
                msg.what = 5;
                alpha += 0.02f;
                msg.obj = alpha;
                mHandler.sendMessage(msg);
            }
        }
    };
    /**
     * 淡化效果
     */
    Runnable DismissTask = new Runnable() {
        @Override
        public void run() {
            while (alpha > 0.4f) {
                try {
                    //4是根据弹出动画时间和减少的透明度计算
                    Thread.sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg = mHandler.obtainMessage();
                msg.what = 5;
                //每次减少0.01，精度越高，变暗的效果越流畅
                alpha -= 0.02f;
                msg.obj = alpha;
                mHandler.sendMessage(msg);
            }
        }
    };
}
