package com.yuecheng.yue.ui.presenter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import com.flyco.animation.SlideEnter.SlideLeftEnter;
import com.flyco.animation.SlideExit.SlideLeftExit;
import com.flyco.animation.SlideExit.SlideRightExit;
import com.yuecheng.yue.R;
import com.yuecheng.yue.db.DBManager;
import com.yuecheng.yue.db.FriendDao;
import com.yuecheng.yue.http.ICommonInteractorCallback;
import com.yuecheng.yue.ui.activity.YUE_IAddFriendView;
import com.yuecheng.yue.ui.bean.AddFridendBean;
import com.yuecheng.yue.ui.bean.SearchedFriendBean;
import com.yuecheng.yue.ui.bean.YUE_SPsave;
import com.yuecheng.yue.ui.dialog.AddFriendDialog;
import com.yuecheng.yue.ui.interactor.YUE_IAddFriendViewInteractor;
import com.yuecheng.yue.ui.interactor.impl.YUE_AddFriendViewInteractorImpl;
import com.yuecheng.yue.util.YUE_LogUtils;
import com.yuecheng.yue.util.YUE_SharedPreferencesUtils;
import com.yuecheng.yue.util.YUE_ToastUtils;
import com.yuecheng.yue.widget.LoadDialog;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/1/25.
 */

public class YUE_AddFriendViewPresenter {
    private String TAG = getClass().getSimpleName();
    private Context mContext;
    private YUE_IAddFriendView mView;
    private YUE_IAddFriendViewInteractor mInteractor;
    private String mAddFriendRequestMessage;
    private AddFriendDialog addFriendDialog;

    public YUE_AddFriendViewPresenter(Context context, YUE_IAddFriendView view) {
        super();
        this.mContext = context;
        this.mView = view;
        mInteractor = new YUE_AddFriendViewInteractorImpl();
    }

    public void queryPersonInfo(String mPhoneNum) {
        LoadDialog.show(mContext);
        mInteractor.getSearchedFriendBean(mPhoneNum, new ICommonInteractorCallback() {
            @Override
            public void loadSuccess(Object object) {
                SearchedFriendBean searchedFriendBean = (SearchedFriendBean) object;
                LoadDialog.dismiss(mContext);
                if (searchedFriendBean.getValue() == null)
                    return;
                switch (searchedFriendBean.getResulecode()) {
                    case 0:
                        YUE_LogUtils.d("YUE_AddFriendViewPresenter", searchedFriendBean);
                        YUE_ToastUtils.showmessage("用户不存在.");
                        break;
                    case 1:
                        YUE_LogUtils.d("YUE_AddFriendViewPresenter", searchedFriendBean);
                        mView.loadResult(searchedFriendBean);
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

    public boolean isFriendOrSelf(String phoneNum) {
        String selfPhoneNum = (String) YUE_SharedPreferencesUtils.getParam(mContext, YUE_SPsave
                .YUE_LOGING_PHONE, "");
        if (phoneNum.trim().equals(selfPhoneNum)) {
            return true;
        } else {
            return DBManager.getInstance().getSession().getFriendDao().queryBuilder()
                    .where
                            (FriendDao
                                    .Properties.PhoneNumber.eq(phoneNum.trim())).list().size() > 0;

        }
    }

    public void showAddFriendDialog(final String userPhone) {
        addFriendDialog = new AddFriendDialog(mContext);
        addFriendDialog.onCreateView();
        addFriendDialog.setUiBeforShow();
        addFriendDialog.setCanceledOnTouchOutside(true);
        addFriendDialog.setCancelable(true);
        addFriendDialog.showAnim(new SlideLeftEnter());
        addFriendDialog.dismissAnim(new SlideLeftExit());
        addFriendDialog.show();
        SpannableString spannableString = new SpannableString("添加用户:" + userPhone + "为好友");
        spannableString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.aeaeae)),
                5, 5 + userPhone.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        addFriendDialog.setTittle(spannableString);
        addFriendDialog.addBtnClickListerer(new AddFriendDialog.BtnClickListerer() {
            @Override
            public void onBtnClick(String messageContent) {
                mAddFriendRequestMessage = messageContent;
                if (TextUtils.isEmpty(mAddFriendRequestMessage))
                    mAddFriendRequestMessage = "我是:" + YUE_SharedPreferencesUtils.getParam(mContext,
                            YUE_SPsave.YUE_LOGING_PHONE, "");
                //发送请求添加好友
                sendMessageToAddFriend(userPhone, mAddFriendRequestMessage);
            }
        });

    }

    private void sendMessageToAddFriend(final String userPhone, final String messageContent) {
        YUE_LogUtils.d(TAG, "发送添加好友请求:" + userPhone + "---" + messageContent);
       LoadDialog.show(mContext);
        mInteractor.getAddSomeoneFridendBean(new HashMap() {
            {
                put("userId1", "" + YUE_SharedPreferencesUtils.getParam(mContext,
                        YUE_SPsave.YUE_LOGING_PHONE, ""));
                put("userId2", userPhone);
                put("content", messageContent);
            }
        }, new ICommonInteractorCallback() {
            @Override
            public void loadSuccess(Object object) {
                LoadDialog.dismiss(mContext);
                AddFridendBean addFridendBean = (AddFridendBean) object;
                if (addFridendBean != null) {
                    switch (addFridendBean.getResulecode()) {
                        case 0:
                            addFriendDialog.dismiss();
                            YUE_ToastUtils.showmessage("你已发送过添加请求,请耐心等待对方回复,");
                            break;
                        case 1:
                            addFriendDialog.dismissAnim(new SlideRightExit());
                            addFriendDialog.dismiss();
                            YUE_ToastUtils.showmessage("发送成功");
                            break;
                        case 2:
                            addFriendDialog.dismiss();
                            YUE_ToastUtils.showmessage("发送失败");
                            break;
                    }
                }
            }

            @Override
            public void loadFailed() {
                YUE_LogUtils.d(TAG, "loadFailed");
            }

            @Override
            public void loadCompleted() {
                YUE_LogUtils.d(TAG, "loadCompleted");
            }

            @Override
            public void addDisaposed(Disposable disposable) {
                YUE_LogUtils.d(TAG, "addDisaposed");
            }
        });
    }
}
