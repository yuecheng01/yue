package com.yuecheng.yue.ui.presenter;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

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

    private static List<Friend> friendList = new ArrayList<>();

    //获取数据更新联系人列表
    public void getDataUpdateContact() {
        if (null != friendList && friendList.size() > 0) {
            YUE_LogUtils.d("friendList.size()-->", friendList.size() + "");
            List<YUE_FriendsBean> list = new ArrayList<>();
            for (Friend friend : friendList
                    ) {
                YUE_FriendsBean yueFriendsBean = new YUE_FriendsBean();
                yueFriendsBean.setNickname(friend.getNickName());
                yueFriendsBean.setPhonenum(friend.getPhoneNumber());
                list.add(yueFriendsBean);
            }
            updateContacts(list);
            //friendList.clear();在本页面刷新时调用,并且调用用mInteractor.getFriendsList重新从服务端拉取数据,重新存入此集合中,
            //这里联系人数据缓存至了本地sqlite数据库,无网络的时候只会加载本地数据库存取的联系人信息.刷新时会删除本地信息,从服务端拉取联系人信息,重新存储
        } else {
            pullDataFromSever();
        }
    }

    public void pullDataFromSever() {
        mInteractor.getFriendsList(mUserId, "1", new ICommonInteractorCallback() {
            @Override
            public void loadSuccess(Object object) {
                final YUE_FriendsListBean mFriendsList = (YUE_FriendsListBean) object;
                deleatAllContacts();
                friendList.clear();
                final List<YUE_FriendsBean> list = mFriendsList.getValue();
                YUE_ThreadUtils.runInThread(new Runnable() {
                    @Override
                    public void run() {
                        //todo 存入本地数据库操作
                        insertIntoSql(list);
                    }
                });
                updateContacts(list);
            }

            @Override
            public void loadFailed() {

            }

            @Override
            public void loadCompleted() {
                mView.setRefreshCancle();
            }

            @Override
            public void addDisaposed(Disposable disposable) {

            }
        });
    }

    private void insertIntoSql(List<YUE_FriendsBean> list) {
        for (YUE_FriendsBean yueFriendsBean : list
                ) {
            Friend friend = new Friend(null, yueFriendsBean.getNickname(), null,
                    null, null, null,
                    null, null, yueFriendsBean.getPhonenum());
//            DBManager.getInstance().getSession().getFriendDao().insert(friend); //todo 此处异常
            friendList.add(friend);
        }

        YUE_LogUtils.d("insert--YUE_FriendsBean", list.size() + "insert Success!");
        List<Friend> l = DBManager.getInstance().getSession().getFriendDao().loadAll();
        for (Friend friend : l
                ) {
            YUE_LogUtils.d("结果:-->", friend.getNickName() + "," + friend.getPhoneNumber());
        }
    }

    //删除所有联系人
    private void deleatAllContacts() {
        DBManager.getInstance().getSession().getFriendDao().deleteAll();
    }

    //更新联系人列表
    private void updateContacts(List<YUE_FriendsBean> list) {

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

        }


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
