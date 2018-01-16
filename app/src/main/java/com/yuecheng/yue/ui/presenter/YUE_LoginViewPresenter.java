package com.yuecheng.yue.ui.presenter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.flyco.dialog.widget.NormalDialog;
import com.yuecheng.yue.R;
import com.yuecheng.yue.http.ICommonInteractorCallback;
import com.yuecheng.yue.http.YUE_API;
import com.yuecheng.yue.ui.activity.YUE_ILoginView;
import com.yuecheng.yue.ui.activity.impl.YUE_HomeActivity;
import com.yuecheng.yue.ui.activity.impl.YUE_LoginActivity;
import com.yuecheng.yue.ui.activity.impl.YUE_RegisterProtocolActivity;
import com.yuecheng.yue.ui.bean.YUE_LoginBean;
import com.yuecheng.yue.ui.interactor.YUE_ILoginViewInteractor;
import com.yuecheng.yue.ui.interactor.impl.YUE_LoginViewInteractorImpl;
import com.yuecheng.yue.util.CommonUtils;
import com.yuecheng.yue.util.YUE_LogUtils;
import com.yuecheng.yue.util.anim.ViewAnimationUtils;
import com.yuecheng.yue.widget.InputFilterEx;
import com.yuecheng.yue.widget.YUE_CustomPopWindow;
import com.yuecheng.yue.widget.selector.YUE_BackResUtils;

import io.reactivex.disposables.Disposable;


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
    private YUE_CustomPopWindow mForgetPasswordPop;
    private YUE_CustomPopWindow mNewPasswordPop;
    private YUE_CustomPopWindow mRegisterPop;
    private YUE_CustomPopWindow mGetCodePop;
    private YUE_CustomPopWindow mSetPwdPop;
    private float alpha = 1.0f;
    private TextInputEditText mUserName;
    private TextInputEditText mPassWord;
    private String mUsername;
    private String mUserPassword;
    private TextInputLayout mUserNameLayout;
    private TextInputLayout mPassWordLayout;
    private boolean mIsAppear = false;

    public YUE_LoginViewPresenter(Context c, YUE_LoginActivity a) {
        super();
        this.mYUE_iLoginView = a;
        this.mContext = c;
        mInteractor = new YUE_LoginViewInteractorImpl();
        mOnTouchListener = new MyTouchListener();
    }

    /**
     * 登录页注册按钮功能***************************************************************************************************************************************
     */
    public void register() {
        mIsAppear = false;
        mYUE_iLoginView.btnRandLInVisable();
       /* mNormalDialog = new NormalDialog(mContext).isTitleShow(false)
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
        });*/
        View mContentView = LayoutInflater.from(mContext).inflate(R.layout.register_dialog_layout,
                null);
        setRegisterViewEvents(mContentView);
        mRegisterPop = new YUE_CustomPopWindow.PopWindowBuilder(mContext)
                .setView(mContentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                .setAnimationStyle(R.style.PopupAnimation)
                .setFocusable(true)
                .setTouchable(true)
                .setBackGround(new ColorDrawable(0))
                .setOutsideTouchable(true)
                .setOnDissmissListener(mOnTouchListener)
                .create()
                .showAtLocation(mYUE_iLoginView.getViewParent(), Gravity.CENTER, 0, 0);
    }

    private void setRegisterViewEvents(View mContentView) {
        ImageView mBack = (ImageView) mContentView.findViewById(R.id.back);
        TextInputEditText mUserName = (TextInputEditText) mContentView.findViewById(R.id.username);
        Button mNextStep = (Button) mContentView.findViewById(R.id.register);
        TextView mRegisterProtocol = (TextView) mContentView.findViewById(R.id.register_protocol);
        mNextStep.setBackground(YUE_BackResUtils.getInstance(mContext).getLoginDrawableSelector());
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRegisterPop.dissmiss();
            }
        });
        mNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsAppear = true;
                mRegisterPop.dissmiss();
                View mContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_reggetcode_layout,
                        null);
                setSendCode(mContentView);
                mGetCodePop = new YUE_CustomPopWindow.PopWindowBuilder(mContext)
                        .setView(mContentView)
                        .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                        .setAnimationStyle(R.style.PopupAnimation)
                        .setFocusable(true)
                        .setTouchable(true)
                        .setBackGround(new ColorDrawable(0))
                        .setOutsideTouchable(true)
                        .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                mRegisterPop.showAtLocation(mYUE_iLoginView.getViewParent(), Gravity.CENTER, 0, 0);
                            }
                        })
                        .create()
                        .showAtLocation(mYUE_iLoginView.getViewParent(), Gravity.CENTER, 0, 0);
            }
        });
        SpannableString spannableString = new SpannableString("点击先一步即表示同意《YUE服务协议》");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(CommonUtils.getColorByAttrId(mContext,R.attr.colorPrimary));
        UnderlineSpan underlineSpan = new UnderlineSpan();
        spannableString.setSpan(underlineSpan, 10,spannableString.length(), Spanned
                .SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(colorSpan, 10, spannableString.length(), Spanned
                .SPAN_INCLUSIVE_EXCLUSIVE);
        mRegisterProtocol.setText(spannableString);
        mRegisterProtocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mYUE_iLoginView.jumpToActivity(YUE_RegisterProtocolActivity.class);
            }
        });
    }

    private void setSendCode(View mContentView) {
        ImageView mBack = (ImageView) mContentView.findViewById(R.id.back);
        TextInputEditText mCode = (TextInputEditText) mContentView.findViewById(R.id.et_code);
        Button mNextStep = (Button) mContentView.findViewById(R.id.bt_next);
        mNextStep.setBackground(YUE_BackResUtils.getInstance(mContext).getLoginDrawableSelector());
        final TextView mBtnCode = (TextView) mContentView.findViewById(R.id.get_code);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetCodePop.dissmiss();
            }
        });
        //获取验证码按钮
        mBtnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CountDownTimer(60000, 1000) {
                    @Override
                    public void onTick(long l) {
                        mBtnCode.setText(l / 1000 + "/SECONDS");
                        mBtnCode.setClickable(false);
                    }

                    @Override
                    public void onFinish() {
                        mBtnCode.setText("重新获取验证码");
                        mBtnCode.setClickable(true);
                    }
                }.start();
            }
        });
        mNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsAppear = true;
                mGetCodePop.dissmiss();
                mRegisterPop.dissmiss();
                View mContentView = LayoutInflater.from(mContext).inflate(R.layout.reg_setpassword_layout,
                        null);
                setRegPwd(mContentView);
                mSetPwdPop = new YUE_CustomPopWindow.PopWindowBuilder(mContext)
                        .setView(mContentView)
                        .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                        .setAnimationStyle(R.style.PopupAnimation)
                        .setFocusable(true)
                        .setTouchable(true)
                        .setBackGround(new ColorDrawable(0))
                        .setOutsideTouchable(true)
                        .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                mRegisterPop.showAtLocation(mYUE_iLoginView.getViewParent(), Gravity.CENTER, 0, 0);
                            }
                        })
                        .create()
                        .showAtLocation(mYUE_iLoginView.getViewParent(), Gravity.CENTER, 0, 0);
            }
        });
    }

    private void setRegPwd(View mContentView) {
        ImageView mBack = (ImageView) mContentView.findViewById(R.id.back);
        TextInputEditText mPwd = (TextInputEditText) mContentView.findViewById(R.id.userpwd);
        TextInputEditText mPwdCofirm = (TextInputEditText) mContentView.findViewById(R.id.passwordcofirm);
        Button mConfirm = (Button) mContentView.findViewById(R.id.confirm);
        mConfirm.setBackground(YUE_BackResUtils.getInstance(mContext).getLoginDrawableSelector());
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSetPwdPop.dissmiss();
            }
        });
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsAppear = false;
                mSetPwdPop.dissmiss();
                mGetCodePop.dissmiss();
                mRegisterPop.dissmiss();
            }
        });
    }

    /**
     * 登录页登录按钮功能*************************************************************************************************************************************
     */
    public void login() {
        mIsAppear = false;
        mYUE_iLoginView.btnRandLInVisable();
        View mContentView = LayoutInflater.from(mContext).inflate(R.layout.logindialog_layout,
                null);
        handleContentView(mContentView);
        mYUE_customPopWindow = new YUE_CustomPopWindow.PopWindowBuilder(mContext)
                .setView(mContentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                .setAnimationStyle(R.style.PopupAnimation)
                .setFocusable(true)
                .setTouchable(true)
                .setBackGround(new ColorDrawable(0))
                .setOutsideTouchable(true)
                .setOnDissmissListener(mOnTouchListener)
                .create()
                .showAtLocation(mYUE_iLoginView.getViewParent(), Gravity.CENTER, 0, 0);

    }

    //登陆界面内控件事件
    private void handleContentView(View mContentView) {
        Button mLogin = (Button) mContentView.findViewById(R.id.login);
        mLogin.setBackground(YUE_BackResUtils.getInstance(mContext).getLoginDrawableSelector());

        CheckBox mRememberPassword = (CheckBox) mContentView.findViewById(R.id.rememberpwd);
        String mIsRemPwd = mInteractor.getIsRemPwd().trim();
        if (!TextUtils.isEmpty(mIsRemPwd))
            switch (mIsRemPwd) {
                case "1":
                    mRememberPassword.setChecked(true);
                    break;
                case "-1":
                    mRememberPassword.setChecked(false);
                    break;
            }
        mRememberPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mInteractor.saveRememberPwd();
                } else {
                    mInteractor.cancleRememberPwd();
                }
            }
        });
        mUserName = (TextInputEditText) mContentView.findViewById(R.id.username);
        mPassWord = (TextInputEditText) mContentView.findViewById(R.id.password);
        if (!TextUtils.isEmpty(mInteractor.getUserName().trim())) {
            mUserName.setText(mInteractor.getUserName().trim());
            mPassWord.setText(mInteractor.getUserPassword().trim());
        }
        mUserNameLayout = (TextInputLayout) mContentView.findViewById(R.id
                .usernamecontainer);
        mPassWordLayout = (TextInputLayout) mContentView.findViewById(R.id
                .passwordcontainer);
        InputFilter[] filters = {new InputFilterEx(16)};
        mUserName.setFilters(filters);
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
        //登录按钮事件
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsername = mUserName.getText().toString().trim();
                mUserPassword = mPassWord.getText().toString().trim();
                if (null != mUsername && null != mPassWord && "" != mUsername && "" !=
                        mUserPassword) {
                    YUE_LogUtils.d("mUsername---->", mUsername);
                    YUE_LogUtils.d("mUserPassword---->", mUserPassword);
                    mInteractor.getLoginRequestData(mUsername, mUserPassword, new ICommonInteractorCallback() {
                        @Override
                        public void loadSuccess(Object object) {
                            YUE_LoginBean mYUE_LoginBean = (YUE_LoginBean) object;
                            YUE_LogUtils.d("resultCode-->", mYUE_LoginBean.getResuletcode());
                            switch (mYUE_LoginBean.getResuletcode()) {
                                case 0:
                                    if (null != mYUE_iLoginView)
                                        ViewAnimationUtils.shake(mUserNameLayout);
                                    ViewAnimationUtils.shake(mPassWordLayout);
                                    mYUE_iLoginView.showMessage(mContext.getResources().getString(R
                                            .string.faillogin));
                                    break;
                                case 1:
                                    //成功登陆
                                    if (null != mYUE_LoginBean && null != mInteractor) {
                                        YUE_LogUtils.i("token---->", "token---->" + mYUE_LoginBean
                                                .getValue().getToken());
                                        mInteractor.saveNandP(mUsername, mUserPassword);
                                        if (null != mYUE_LoginBean.getValue() &&
                                                (!mYUE_LoginBean.getValue().getToken().equals(""))) {
                                            mInteractor.saveRongToken(mYUE_LoginBean.getValue().getToken());
                                            mYUE_customPopWindow.dissmiss();
                                            mYUE_iLoginView.finishActivity();
                                            mYUE_iLoginView.jumpToActivity(YUE_HomeActivity.class);
                                        }
                                    }
                                    break;
                            }
                        }

                        @Override
                        public void loadFailed() {

                        }

                        @Override
                        public void loadCompleted() {

                        }

                        @Override
                        public void addDisaposed(Disposable disposable) {

                        }
                    });
                } else {
                    mYUE_iLoginView.showMessage(mContext.getResources().getString(R.string
                            .nullpoint));
                }
            }
        });
        //返回按钮事件
        mContentView.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mYUE_customPopWindow.dissmiss();
            }
        });
        //忘记密码点击事件
        mContentView.findViewById(R.id.forgetpassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsAppear = true;
                mYUE_customPopWindow.dissmiss();
//                mYUE_iLoginView.btnRandLInVisable();
                View mContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_forgetpassword_layout,
                        null);
                setForgetPasswordEvents(mContentView);
                mForgetPasswordPop = new YUE_CustomPopWindow.PopWindowBuilder(mContext)
                        .setView(mContentView)
                        .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                        .setAnimationStyle(R.style.PopupAnimation)
                        .setFocusable(true)
                        .setTouchable(true)
                        .setBackGround(new ColorDrawable(0))
                        .setOutsideTouchable(true)
                        .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                mYUE_customPopWindow.showAtLocation(mYUE_iLoginView.getViewParent(), Gravity.CENTER, 0, 0);
                            }
                        })
                        .create()
                        .showAtLocation(mYUE_iLoginView.getViewParent(), Gravity.CENTER, 0, 0);
            }
        });
    }

    //忘记密码页面事件
    private void setForgetPasswordEvents(View mContentView) {
        ImageView mBack = (ImageView) mContentView.findViewById(R.id.back);
        TextInputEditText mPhoneNum = (TextInputEditText) mContentView.findViewById(R.id.username);
        TextInputEditText mCode = (TextInputEditText) mContentView.findViewById(R.id.et_code);
        final TextView mBtnCode = (TextView) mContentView.findViewById(R.id.get_code);
        Button mNextStap = (Button) mContentView.findViewById(R.id.bt_next);

        mNextStap.setBackground(YUE_BackResUtils.getInstance(mContext).getLoginDrawableSelector());
        //顶部返回按钮
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mForgetPasswordPop.dissmiss();
            }
        });
        //获取验证码按钮
        mBtnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CountDownTimer(60000, 1000) {
                    @Override
                    public void onTick(long l) {
                        mBtnCode.setText(l / 1000 + "/SECONDS");
                        mBtnCode.setClickable(false);
                    }

                    @Override
                    public void onFinish() {
                        mBtnCode.setText("重新获取验证码");
                        mBtnCode.setClickable(true);
                    }
                }.start();
            }
        });
        //下一步按钮
        mNextStap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsAppear = true;
                mForgetPasswordPop.dissmiss();
                mYUE_customPopWindow.dissmiss();
                View mContentView = LayoutInflater.from(mContext).inflate(R.layout.setnewpassword_layout,
                        null);
                setNewPassword(mContentView);

                mNewPasswordPop = new YUE_CustomPopWindow.PopWindowBuilder(mContext)
                        .setView(mContentView)
                        .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                        .setAnimationStyle(R.style.PopupAnimation)
                        .setFocusable(true)
                        .setTouchable(true)
                        .setBackGround(new ColorDrawable(0))
                        .setOutsideTouchable(true)
                        .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                mYUE_customPopWindow.showAtLocation(mYUE_iLoginView.getViewParent(), Gravity.CENTER, 0, 0);
                            }
                        })
                        .create()
                        .showAtLocation(mYUE_iLoginView.getViewParent(), Gravity.CENTER, 0, 0);
            }
        });
    }

    //设置新密码事件
    private void setNewPassword(View mContentView) {
        ImageView mBack = (ImageView) mContentView.findViewById(R.id.back);
        TextInputEditText mNewPassword = (TextInputEditText) mContentView.findViewById(R.id.username);
        Button mConfirm = (Button) mContentView.findViewById(R.id.confirm);

        mConfirm.setBackground(YUE_BackResUtils.getInstance(mContext).getLoginDrawableSelector());
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewPasswordPop.dissmiss();
            }
        });
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewPasswordPop.dissmiss();
                mForgetPasswordPop.dissmiss();
            }
        });
    }

    class MyTouchListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            if (mIsAppear) {
                mIsAppear = false;
            } else {
                mYUE_iLoginView.btnRandLVisable();
            }
        }
    }

}
