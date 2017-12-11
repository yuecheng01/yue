package com.yuecheng.yue.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yuecheng.yue.http.YUE_API;
import com.yuecheng.yue.http.pinyin.CharacterParser;
import com.yuecheng.yue.ui.bean.YUE_FriendsBean;
import com.yuecheng.yue.ui.bean.YUE_FriendsListBean;
import com.yuecheng.yue.ui.bean.YUE_SPsave;
import com.yuecheng.yue.ui.fragment.YUE_IContactsFragment;
import com.yuecheng.yue.ui.interactor.YUE_IContactsInteractor;
import com.yuecheng.yue.ui.interactor.impl.YUE_ContactsInteractorImpl;
import com.yuecheng.yue.util.YUE_SharedPreferencesUtils;

import java.util.List;


/**
 * Created by yuecheng on 2017/11/11.
 */
public class YUE_ContactsFragmentPresenter extends YUE_BasePresenter {
    private Context mContext;
    private YUE_IContactsFragment mYUE_iContactsFragment;
    private YUE_IContactsInteractor mInteractor;
    private YUE_FriendsListBean mFriendsList;
    private CharacterParser mCharacterParser;
    private String mId;
    private String mCacheName;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String s = (String) msg.getData().get("FriendsList");
                    if (null != s) {
                        mFriendsList = JSON.parseObject(s, YUE_FriendsListBean.class);
                        mCharacterParser = CharacterParser.getInstance();
                        List<YUE_FriendsBean> list = mFriendsList.getValue();
                        for (int i = 0; i < list.size(); i++) {
                                list.get(i).setNickNameSpelling(mCharacterParser.getSpelling(list.get(i).getNickname()));
                                list.get(i).setDisplayNameSpelling(mCharacterParser.getSpelling(list.get(i).getDisplayname()));
                        }
                        mYUE_iContactsFragment.loadFriendsList(list);
                    }
                    break;
            }
        }
    };

    public YUE_ContactsFragmentPresenter(Context context, YUE_IContactsFragment yue_iContactsFragment) {
        super();
        this.mContext = context;
        this.mYUE_iContactsFragment = yue_iContactsFragment;
        this.mInteractor = new YUE_ContactsInteractorImpl();
    }

    public void getData() {
        mInteractor.getFriendsList(YUE_API.GETFRIENDSLIST,
                "userId1", "statues", mUserId, "1",
                1, "FriendsList", mHandler);
    }

    public void updatePersonalUI() {
         mId = (String) YUE_SharedPreferencesUtils.getParam(mContext, YUE_SPsave.YUE_LOGING_PHONE,"");
         mCacheName = "yuecheng";
        mYUE_iContactsFragment.setpersonalUI(mId,mCacheName);
    }

    public void handleFriendDataForSort() {
        for (YUE_FriendsBean friend : mYUE_iContactsFragment.getFriendList()) {
            if (friend.isExitsDisplayName()) {
                String letters = replaceFirstCharacterWithUppercase(friend.getDisplayNameSpelling());
                friend.setLetters(letters);
            } else {
                String letters = replaceFirstCharacterWithUppercase(friend.getNickNameSpelling());
                friend.setLetters(letters);
            }
        }
    }

    private String replaceFirstCharacterWithUppercase(String spelling) {

        if (!TextUtils.isEmpty(spelling)) {
            char first = spelling.charAt(0);
            char newFirst = first;
            if (first >= 'a' && first <= 'z') {
                newFirst -= 32;
            }
            return spelling.replaceFirst(String.valueOf(first), String.valueOf(newFirst));
        } else {
            return "#";
        }
    }
}
