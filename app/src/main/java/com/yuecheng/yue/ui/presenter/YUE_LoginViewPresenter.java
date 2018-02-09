package com.yuecheng.yue.ui.presenter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.yuecheng.yue.R;
import com.yuecheng.yue.app.YUE_UserInfoManager;
import com.yuecheng.yue.http.ICommonInteractorCallback;
import com.yuecheng.yue.ui.activity.YUE_ILoginView;
import com.yuecheng.yue.ui.activity.impl.YUE_HomeActivity;
import com.yuecheng.yue.ui.activity.impl.YUE_LoginActivity;
import com.yuecheng.yue.ui.activity.impl.YUE_RegisterProtocolActivity;
import com.yuecheng.yue.ui.bean.IsRegisterOrNotBean;
import com.yuecheng.yue.ui.bean.MessageCodeBean;
import com.yuecheng.yue.ui.bean.RegisterBean;
import com.yuecheng.yue.ui.bean.ResetPasswordBean;
import com.yuecheng.yue.ui.bean.YUE_LoginBean;
import com.yuecheng.yue.ui.bean.YUE_SPsave;
import com.yuecheng.yue.ui.interactor.YUE_ILoginViewInteractor;
import com.yuecheng.yue.ui.interactor.impl.YUE_LoginViewInteractorImpl;
import com.yuecheng.yue.util.CodeUtils;
import com.yuecheng.yue.util.CommonUtils;
import com.yuecheng.yue.util.YUE_LogUtils;
import com.yuecheng.yue.util.YUE_SharedPreferencesUtils;
import com.yuecheng.yue.util.YUE_ToastUtils;
import com.yuecheng.yue.util.anim.ViewAnimationUtils;
import com.yuecheng.yue.widget.InputFilterEx;
import com.yuecheng.yue.ui.dialog.PicCodeConfirDialog;
import com.yuecheng.yue.widget.LoadDialog;
import com.yuecheng.yue.widget.YUE_CustomPopWindow;
import com.yuecheng.yue.widget.selector.YUE_BackResUtils;

import io.reactivex.disposables.Disposable;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;


/**
 * Created by yuecheng on 2017/10/29.
 */

public class YUE_LoginViewPresenter {
    private YUE_ILoginView mView;
    private YUE_ILoginViewInteractor mInteractor;
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
    private String mRegPhoneNum;//注册的手机号
    private String mRegNickname;//注册的网名(昵称)
    private String mMessageCode;//注册发送的验证码
    private String mRegPassword;//注册设置的密码
    private String mRegPasswordConfir;//注册确认密码
    private String mResetPwdUserId;//重置密码的用户电话;


    public YUE_LoginViewPresenter(Context c, YUE_LoginActivity a) {
        super();
        this.mView = a;
        this.mContext = c;
        mInteractor = new YUE_LoginViewInteractorImpl();
        mOnTouchListener = new MyTouchListener();
    }

    /**
     * ***********************************************************************登录页注册按钮功能****************************************************************
     */
    public void register() {
        mIsAppear = false;
        mView.btnRandLInVisable();
       /* mNormalDialog = new NormalDialog(mContext).isTitleShow(false)
                .content(mContext.getResources().getString(R.string.registerinfo))
                .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                .btnNum(1).btnText(mContext.getResources().getString(R.string.infr));
        mNormalDialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                mNormalDialog.dismiss();
                mView.btnRandLVisable();
            }
        });
        mNormalDialog.show();
        mNormalDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mView.btnRandLVisable();
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
                .showAtLocation(mView.getViewParent(), Gravity.CENTER, 0, 0);
    }

    private void setRegisterViewEvents(View mContentView) {
        ImageView mBack = (ImageView) mContentView.findViewById(R.id.back);
        final TextInputEditText mUserName = (TextInputEditText) mContentView.findViewById(R.id.username);
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
                mRegPhoneNum = mUserName.getText().toString();
                if (!CommonUtils.isMobileNO(mRegPhoneNum)) {
                    YUE_ToastUtils.showmessage("请输入正确的手机号码!");
                    return;
                }
                LoadDialog.show(mContext);
                mInteractor.getIsRegisterOrNotBean(mRegPhoneNum, new ICommonInteractorCallback() {
                    @Override
                    public void loadSuccess(Object object) {
                        LoadDialog.dismiss(mContext);
                        IsRegisterOrNotBean isRegisterOrNotBean = (IsRegisterOrNotBean) object;
                        switch (isRegisterOrNotBean.getResulecode()) {
                            case 0:
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
                                                if (sendCountMessage != null)
                                                    sendCountMessage.cancel();
                                                mRegisterPop.showAtLocation(mView.getViewParent(), Gravity.CENTER, 0, 0);
                                            }
                                        })
                                        .create()
                                        .showAtLocation(mView.getViewParent(), Gravity.CENTER, 0, 0);
                                break;
                            case 1:
                                YUE_ToastUtils.showmessage("该账号已存在,如遗忘密码,请返回登录页面点击\"忘记密码\"找回密码.");
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
            }
        });
        SpannableString spannableString = new SpannableString("点击下一步即表示同意《YUE服务协议》");
        spannableString.setSpan(new ClickableSpan() {
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(CommonUtils.getColorByAttrId(mContext, R.attr.colorPrimary));      //设置颜色
                ds.setUnderlineText(true);      //设置下划线
            }

            @Override
            public void onClick(View view) {
                mView.jumpToActivity(YUE_RegisterProtocolActivity.class);
            }
        }, 10, spannableString.length(), Spanned
                .SPAN_INCLUSIVE_EXCLUSIVE);
        mRegisterProtocol.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        mRegisterProtocol.setText(spannableString);
        mRegisterProtocol.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }

    private TextView mBtnCode;//确定按钮
    private TextInputEditText mCode;//填写短信验证码的输入框

    private void setSendCode(View mContentView) {
        ImageView mBack = (ImageView) mContentView.findViewById(R.id.back);
        mCode = (TextInputEditText) mContentView.findViewById(R.id.et_code);
        Button mNextStep = (Button) mContentView.findViewById(R.id.bt_next);
        mNextStep.setBackground(YUE_BackResUtils.getInstance(mContext).getLoginDrawableSelector());
        mBtnCode = (TextView) mContentView.findViewById(R.id.get_code);
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
                //如果按钮显示请重新输入,则是第一次输入的短信验证码有误,此时不在再次验证图形验证码,可直接再次获取短信验证码
                if (mBtnCode.getText().toString().equals(mContext.getResources().getString(R
                        .string.message_code_isconfir))) {
                    reset_mStringBuilder();
                    sendConfirCode(mCode.getText().toString());
                } else {
                    showPicCodeConfir();
                }
            }
        });
        mNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMessageCode = mCode.getText().toString();
                if (mMessageCode.length() == 4) {
                    //TODO 请求server验证
                    LoadDialog.show(mContext);
                    mInteractor.confirMessageCode(mMessageCode, new
                            ICommonInteractorCallback() {
                                @Override
                                public void loadSuccess(Object object) {
                                    LoadDialog.dismiss(mContext);
                                    MessageCodeBean messageCodeBean = (MessageCodeBean) object;
                                    switch (messageCodeBean.getResulecode()) {
                                        case 0://验证失败
                                            YUE_ToastUtils.showmessage("验证码有误,请重新输入");
                                            break;
                                        case 1://验证成功
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
                                                            mRegisterPop.showAtLocation(mView.getViewParent(), Gravity.CENTER, 0, 0);
                                                        }
                                                    })
                                                    .create()
                                                    .showAtLocation(mView.getViewParent(), Gravity.CENTER, 0, 0);
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
                    mIsAppear = true;
                } else {
                    YUE_ToastUtils.showmessage("请正确填写您收到的4位数验证码");
                    return;
                }
            }
        });
    }

    private StringBuilder mStringBuilder = null;

    //弹出图形验证码
    private void showPicCodeConfir() {
        final PicCodeConfirDialog picCodeConfirDialog = new PicCodeConfirDialog(mContext);
        picCodeConfirDialog.onCreateView();
        picCodeConfirDialog.setUiBeforShow();
        //点击空白区域能不能退出
        picCodeConfirDialog.setCanceledOnTouchOutside(true);
        //按返回键能不能退出
        picCodeConfirDialog.setCancelable(true);
        picCodeConfirDialog.show();
        picCodeConfirDialog.addOnPicCodeClickListener(new PicCodeConfirDialog.OnPicCodeClickListener() {
            @Override
            public void onPicCodeClick(String inputStr) {
                picCodeConfirDialog.reSetPicCode(CodeUtils.getInstance().createBitmap());
            }

            @Override
            public void onConfirClick() {
                String getInput = picCodeConfirDialog.getInput();
                if (getInput.equalsIgnoreCase((String) YUE_SharedPreferencesUtils.getParam
                        (mContext, YUE_SPsave.PIC_CODE_CONFIR, "0000"))) {
                    picCodeConfirDialog.dismiss();
                    //图形校验码验证通过执行
                    reset_mStringBuilder();
                    mStringBuilder.append("校验通过,");
                    //发送短信验证码
                    sendConfirCode(mCode.getText().toString());
                } else {
                    //图形校验码验证失败执行
                    YUE_ToastUtils.showmessage("您输入的校验码有误,请重新输入!");
                    picCodeConfirDialog.setInput("");
                    picCodeConfirDialog.reSetPicCode(CodeUtils.getInstance().createBitmap());
                }
            }
        });
    }

    private void reset_mStringBuilder() {
        if (mStringBuilder == null) {
            mStringBuilder = new StringBuilder();
        } else {
            mStringBuilder = null;
            mStringBuilder = new StringBuilder();
        }
    }

    private SendCountMessage sendCountMessage;

    private void sendConfirCode(String getInput) {
        //TODO 请求server端获取手机短信验证码校验注册
        //..........
        mStringBuilder.append("验证码已发送至手机,请注意查收.");
        YUE_ToastUtils.showmessage(mStringBuilder);
        sendCountMessage = new SendCountMessage();
        sendCountMessage.start();
    }

    public class SendCountMessage extends CountDownTimer {
        public SendCountMessage() {
            super(60000, 1000);
        }

        @Override
        public void onTick(long l) {
            mBtnCode.setText(l / 1000 + "/SECONDS");
            mBtnCode.setClickable(false);
        }

        @Override
        public void onFinish() {
            mBtnCode.setText(mContext.getResources().getString(R.string.message_code_isconfir));
            mBtnCode.setClickable(true);
        }
    }

    private void setRegPwd(View mContentView) {
        ImageView mBack = (ImageView) mContentView.findViewById(R.id.back);
        final TextInputEditText mNickName = (TextInputEditText) mContentView.findViewById(R.id
                .usernickname);
        final TextInputEditText mPwd = (TextInputEditText) mContentView.findViewById(R.id.userpwd);
        final TextInputEditText mPwdCofirm = (TextInputEditText) mContentView.findViewById(R.id.passwordcofirm);
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
                mRegNickname = mNickName.getText().toString();
                mRegPassword = mPwd.getText().toString();
                mRegPasswordConfir = mPwdCofirm.getText().toString();
                if (!mRegPassword.equals("") && !mRegPasswordConfir.equals("") && mRegPassword.equals
                        (mRegPasswordConfir)) {
                    LoadDialog.show(mContext);
                    mInteractor.submitRegisterInfo(mRegPhoneNum, mRegNickname, mRegPassword,
                            mRegPasswordConfir, new ICommonInteractorCallback() {
                                @Override
                                public void loadSuccess(Object object) {
                                    LoadDialog.dismiss(mContext);
                                    RegisterBean registerBean = (RegisterBean) object;
                                    switch (registerBean.getResulecode()) {
                                        case 0://用户已存在,注册失败
                                            YUE_ToastUtils.showmessage("该用户已存在,请直接登录!");
                                            break;
                                        case 1://注册成功
                                            YUE_ToastUtils.showmessage("注册成功!");
                                            showDialogToUserToRememberInfo(mRegPhoneNum, mRegPassword);
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
                    mSetPwdPop.dissmiss();
                    mGetCodePop.dissmiss();
                    mRegisterPop.dissmiss();
                } else if (mRegPassword.equals("") || mRegPasswordConfir.equals("")) {
                    YUE_ToastUtils.showmessage("密码不能为空,请输入密码!");
                } else {
                    YUE_ToastUtils.showmessage("请确定输入的密码一致!");
                }
            }
        });
    }

    /**
     * 弹出提示框
     *
     * @param username
     * @param password
     */
    private void showDialogToUserToRememberInfo(String username, String password) {
        final MaterialDialog dialog = new MaterialDialog(mContext);
        dialog.btnNum(1)
                .content(
                        "您好,如下是您注册的信息,请牢记;后期如有遗忘,可在登录页点击\"忘记密码\"找回.\n" +
                                "账号:" + username + "\n密码:" + password)//提示内容
                .btnText("确定")//按钮
                .btnPressColor(mContext.getResources().getColor(R.color.aeaeae))
                .btnTextColor(CommonUtils.getColorByAttrId(mContext, R.attr.colorPrimary))
                .showAnim(new BounceTopEnter())//
                .dismissAnim(new SlideBottomExit())//
                .show();
        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dialog.dismiss();
            }
        });
    }

    /**
     * *******************************************************************登录页登录按钮功能******************************************************************
     */
    public void login() {
        mIsAppear = false;
        mView.btnRandLInVisable();
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
                .showAtLocation(mView.getViewParent(), Gravity.CENTER, 0, 0);

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
        //设置用户名输入框末尾的图片点击事件:一键清空输入
        mUserName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // mUserName.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = mUserName.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null)
                    return false;
                //如果不是按下事件，不再处理
                if (motionEvent.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (motionEvent.getX() > mUserName.getWidth()
                        - mUserName.getPaddingRight()
                        - drawable.getIntrinsicWidth()) {
                    mUserName.setText("");
                }
                return false;
            }
        });
        if (!TextUtils.isEmpty(mInteractor.getUserName().trim())) {
            mUserName.setText(mInteractor.getUserName().trim());
            mUserName.setSelection(mInteractor.getUserName().trim().length());
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
                    LoadDialog.show(mContext);
                    mInteractor.getLoginRequestData(mUsername, mUserPassword, new ICommonInteractorCallback() {
                        @Override
                        public void loadSuccess(Object object) {
                            LoadDialog.dismiss(mContext);
                            YUE_LoginBean mYUE_LoginBean = (YUE_LoginBean) object;
                            YUE_LogUtils.d("resultCode-->", mYUE_LoginBean.getResuletcode());
                            switch (mYUE_LoginBean.getResuletcode()) {
                                case 0:
                                    if (null != mView)
                                        ViewAnimationUtils.shake(mUserNameLayout);
                                    ViewAnimationUtils.shake(mPassWordLayout);
                                    mView.showMessage(mContext.getResources().getString(R
                                            .string.faillogin));
                                    break;
                                case 1:
                                    //成功登陆
                                    if (null != mYUE_LoginBean && null != mInteractor) {
                                        String token = mYUE_LoginBean.getValue().getToken();
                                        YUE_LogUtils.i("token---->", "token---->" + mYUE_LoginBean
                                                .getValue().getToken());
                                        mInteractor.saveNandP(mUsername, mUserPassword);
                                        mInteractor.saveRongToken(token);
                                        YUE_UserInfoManager.getInstance().upDateContacts();
                                        if (!TextUtils.isEmpty(token)) {
                                            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                                                @Override
                                                public void onTokenIncorrect() {

                                                }

                                                @Override
                                                public void onSuccess(String s) {
                                                    YUE_ToastUtils.showmessage(
                                                            "鏈接成功" + s);
                                                    mYUE_customPopWindow.dissmiss();
                                                    mView.finishActivity();
                                                    mView.jumpToActivity(YUE_HomeActivity.class);
                                                }

                                                @Override
                                                public void onError(RongIMClient.ErrorCode errorCode) {

                                                }
                                            });
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
                    mView.showMessage(mContext.getResources().getString(R.string
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
//                mView.btnRandLInVisable();
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
                                mYUE_customPopWindow.showAtLocation(mView.getViewParent(), Gravity.CENTER, 0, 0);
                            }
                        })
                        .create()
                        .showAtLocation(mView.getViewParent(), Gravity.CENTER, 0, 0);
            }
        });
    }

    //忘记密码页面事件
    private void setForgetPasswordEvents(View mContentView) {
        ImageView mBack = (ImageView) mContentView.findViewById(R.id.back);
        final TextInputEditText mPhoneNum = (TextInputEditText) mContentView.findViewById(R.id.username);
        final TextInputEditText mCode = (TextInputEditText) mContentView.findViewById(R.id.et_code);
        final TextView mBtnCode = (TextView) mContentView.findViewById(R.id.get_code);
        Button mNextStap = (Button) mContentView.findViewById(R.id.bt_next);

        InputFilter[] filters = {new InputFilterEx(16)};
        mPhoneNum.setFilters(filters);
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
                if (CommonUtils.isMobileNO(mPhoneNum.getText().toString())) {
                    LoadDialog.show(mContext);
                    mInteractor.getIsRegisterOrNotBean(mPhoneNum.getText().toString(), new ICommonInteractorCallback() {
                        @Override
                        public void loadSuccess(Object object) {
                            LoadDialog.dismiss(mContext);
                            IsRegisterOrNotBean IsRegisterOrNotBean = (IsRegisterOrNotBean) object;
                            switch (IsRegisterOrNotBean.getResulecode()) {
                                case 0:
                                    YUE_ToastUtils.showmessage("该号码未注册,请您先注册.");
                                    break;
                                case 1:
                                    //图形验证码校验
                                    showPicCodeConfir();
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
                    YUE_ToastUtils.showmessage("请输入正确的手机号码.");
                }

            }
        });
        //下一步按钮
        mNextStap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageCode = mCode.getText().toString();
                if (messageCode.length() == 4 && !TextUtils.isEmpty(mPhoneNum.getText())
                        && CommonUtils.isMobileNO(mPhoneNum.getText().toString())) {
                    //TODO 请求server验证
                    LoadDialog.show(mContext);
                    mInteractor.confirMessageCode(messageCode, new
                            ICommonInteractorCallback() {
                                @Override
                                public void loadSuccess(Object object) {
                                    LoadDialog.dismiss(mContext);
                                    MessageCodeBean messageCodeBean = (MessageCodeBean) object;
                                    switch (messageCodeBean.getResulecode()) {
                                        case 1:
                                            mIsAppear = true;
                                            mForgetPasswordPop.dissmiss();
                                            mYUE_customPopWindow.dissmiss();
                                            mResetPwdUserId = mPhoneNum.getText().toString();
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
                                                            mYUE_customPopWindow.showAtLocation(mView.getViewParent(), Gravity.CENTER, 0, 0);
                                                        }
                                                    })
                                                    .create()
                                                    .showAtLocation(mView.getViewParent(), Gravity.CENTER, 0, 0);
                                            break;
                                        case 0:
                                            YUE_ToastUtils.showmessage("验证码有误,请重新输入");
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
                } else if (messageCode.length() != 4) {
                    YUE_ToastUtils.showmessage("请输入正确的验证码!");
                } else if (!CommonUtils.isMobileNO(mPhoneNum.getText().toString())) {
                    YUE_ToastUtils.showmessage("手机号非法.");
                } else {
                    YUE_ToastUtils.showmessage("请填写完整.");
                }
            }
        });
    }

    //设置新密码事件
    private void setNewPassword(View mContentView) {
        ImageView mBack = (ImageView) mContentView.findViewById(R.id.back);
        final TextInputEditText mNewPassword = (TextInputEditText) mContentView.findViewById(R.id.newpassword);
        final TextInputEditText mNewPasswordFir = (TextInputEditText) mContentView.findViewById(R.id
                .newpasswordfir);
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
                String mNewPasswordStr = mNewPassword.getText().toString();
                String mNewPasswordFirStr = mNewPasswordFir.getText().toString();
                if (!TextUtils.isEmpty(mNewPasswordStr) && !TextUtils.isEmpty(mNewPasswordFirStr) &&
                        mNewPasswordStr
                                .equals(mNewPasswordFirStr)) {
                    LoadDialog.show(mContext);
                    mInteractor.getResetPasswordBean(mResetPwdUserId, mNewPasswordStr,
                            mNewPasswordFirStr, new
                                    ICommonInteractorCallback() {
                                        @Override
                                        public void loadSuccess(Object object) {
                                            LoadDialog.dismiss(mContext);
                                            ResetPasswordBean resetPasswordBean = (ResetPasswordBean) object;
                                            switch (resetPasswordBean.getResulecode()) {
                                                case 0:
                                                    YUE_ToastUtils.showmessage("密码不能喝原密码一样.请重新输入!");
                                                    break;
                                                case 1:
                                                    mNewPasswordPop.dissmiss();
                                                    mForgetPasswordPop.dissmiss();
                                                    YUE_ToastUtils.showmessage("重置密码成功," +
                                                            "欢迎使用新密码登录!");
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
                } else if (TextUtils.isEmpty(mNewPasswordStr) && TextUtils.isEmpty(mNewPasswordFirStr)) {
                    YUE_ToastUtils.showmessage("密码不能为空.");
                } else {
                    YUE_ToastUtils.showmessage("输入不一致,请重新输入");
                }
            }
        });
    }

    class MyTouchListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            if (mIsAppear) {
                mIsAppear = false;
            } else {
                mView.btnRandLVisable();
            }
        }
    }

}
