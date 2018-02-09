package com.yuecheng.yue.ui.presenter;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.yuecheng.yue.app.YUE_UserInfoManager;
import com.yuecheng.yue.db.DBManager;
import com.yuecheng.yue.db.entity.Friend;
import com.yuecheng.yue.http.ICommonInteractorCallback;
import com.yuecheng.yue.http.pinyin.CharacterParser;
import com.yuecheng.yue.provider.ContactProvider;
import com.yuecheng.yue.ui.bean.YUE_FriendsBean;
import com.yuecheng.yue.ui.bean.YUE_FriendsListBean;
import com.yuecheng.yue.ui.fragment.YUE_IContactsFragment;
import com.yuecheng.yue.ui.interactor.YUE_IContactsInteractor;
import com.yuecheng.yue.ui.interactor.impl.YUE_ContactsInteractorImpl;
import com.yuecheng.yue.util.YUE_LogUtils;
import com.yuecheng.yue.util.YUE_ThreadUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;


/**
 * Created by yuecheng on 2017/11/11.
 */
public class YUE_ContactsFragmentPresenter extends YUE_BasePresenter {
    private Context mContext;
    private YUE_IContactsFragment mView;
    private YUE_IContactsInteractor mInteractor;
    private CharacterParser mCharacterParser;
    private String mCacheName;


    public YUE_ContactsFragmentPresenter(Context context, YUE_IContactsFragment yue_iContactsFragment) {
        super();
        this.mContext = context;
        this.mView = yue_iContactsFragment;
        this.mInteractor = new YUE_ContactsInteractorImpl();
        mCharacterParser = CharacterParser.getInstance();
    }

    private  List<Friend> friendList;

    //获取数据更新联系人列表
    public void getDataUpdateContact() {
        //从本地数据库读取联系人更新列表
        YUE_UserInfoManager.getInstance().getFriendList(new ICommonInteractorCallback() {
            @Override
            public void loadSuccess(Object object) {
                friendList =(List<Friend>)object;
                if (null != friendList) {
                    List<YUE_FriendsBean> list = new ArrayList<>();
                    for (Friend friend : friendList
                            ) {
                        YUE_FriendsBean yueFriendsBean = new YUE_FriendsBean();
                        yueFriendsBean.setNickname(friend.getNickName());
                        yueFriendsBean.setPhonenum(friend.getPhoneNumber());
                        list.add(yueFriendsBean);
                    }
                    updateContacts(list);
                } else {

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

    //更新联系人列表
    private void updateContacts(List<YUE_FriendsBean> list) {

       /* //测试contentprovider
        Cursor c = mContext.getContentResolver().query(ContactProvider.URI_CONTACT, null,
                null, null, null);
        try {
            int columnCount = c.getColumnCount();
            if (null != c && columnCount > 0)
                while (c.moveToNext()) {
                    for (int i = 0; i < columnCount; i++) {
                        YUE_LogUtils.d("结果-->", c.getString(i) + "");
                    }
                }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }*/
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setNickNameSpelling(mCharacterParser.getSpelling(list.get(i).getNickname()));
            list.get(i).setDisplayNameSpelling(mCharacterParser.getSpelling(list.get(i).getDisplayname()));
        }
        mView.loadFriendsList(list);
    }

    public void updatePersonalUI() {
        mCacheName = "yuecheng";
        mView.setpersonalUI(mUserId, mCacheName);
    }

    public void handleFriendDataForSort() {
        for (YUE_FriendsBean friend : mView.getFriendList()) {
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
