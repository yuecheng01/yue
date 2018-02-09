package com.yuecheng.yue.ui.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.yuecheng.yue.app.YUE_UserInfoManager;
import com.yuecheng.yue.db.entity.Friend;
import com.yuecheng.yue.http.ICommonInteractorCallback;
import com.yuecheng.yue.http.pinyin.CharacterParser;
import com.yuecheng.yue.http.pinyin.PinyinComparator2;
import com.yuecheng.yue.ui.activity.impl.YUE_ISelectFriendsView;
import com.yuecheng.yue.util.YUE_LogUtils;

import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/1/28.
 */

public class YUE_SelectFriendsPresenter {
    private Context mContext;
    private YUE_ISelectFriendsView mView;
    private PinyinComparator2 mPinyinComparator;
    private CharacterParser mCharacterParser;

    public YUE_SelectFriendsPresenter(Context context, YUE_ISelectFriendsView view) {
        super();
        this.mContext = context;
        this.mView = view;
        mPinyinComparator = PinyinComparator2.getInstance();
        mCharacterParser = CharacterParser.getInstance();
    }

    public void setContactsList() {
        YUE_UserInfoManager.getInstance().getFriendList(new ICommonInteractorCallback() {
            @Override
            public void loadSuccess(Object object) {
                List<Friend> friendList = (List<Friend>) object;
                YUE_LogUtils.d("yue_selectfriends", friendList.size() + "");
                setFriendListSpelling(friendList);
                handleFriendDataForSort(friendList);
                // 根据a-z进行排序源数据
                Collections.sort(friendList, mPinyinComparator);
                mView.updateAdapter(friendList);
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

    private void setFriendListSpelling(List<Friend> friendList) {
        for (Friend friend : friendList
                ) {
            if (!TextUtils.isEmpty(friend.getNickName())) {
                YUE_LogUtils.d("spelling:", mCharacterParser.getSpelling(friend.getNickName()));
                friend.setNickNameSpelling(mCharacterParser.getSpelling(friend.getNickName()));
            } else if (!TextUtils.isEmpty(friend.getDisplayName())) {
                friend.setDisplayNameSpelling(mCharacterParser.getSpelling(friend.getDisplayName()));
            }
        }
    }

    private void handleFriendDataForSort(List<Friend> friendList) {
        for (Friend friend : friendList
                ) {
            if (!TextUtils.isEmpty(friend.getDisplayName())) {
                String letters = replaceFirstCharacterWithUppercase(friend.getDisplayNameSpelling());
                friend.setLetters(letters);
            } else {
                String letters = replaceFirstCharacterWithUppercase(friend.getNickNameSpelling());
                YUE_LogUtils.d("letters:", letters);
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
