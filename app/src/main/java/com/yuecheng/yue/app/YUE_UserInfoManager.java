package com.yuecheng.yue.app;

import android.content.Context;

import com.yuecheng.yue.db.DBManager;
import com.yuecheng.yue.db.entity.Friend;
import com.yuecheng.yue.http.ApiServicesManager;
import com.yuecheng.yue.http.ICommonInteractorCallback;
import com.yuecheng.yue.ui.bean.YUE_FriendsBean;
import com.yuecheng.yue.ui.bean.YUE_FriendsListBean;
import com.yuecheng.yue.ui.bean.YUE_SPsave;
import com.yuecheng.yue.util.YUE_LogUtils;
import com.yuecheng.yue.util.YUE_SharedPreferencesUtils;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 管理用户信息,当新的用户登录时将会更新用户信息,此外 从本地数据库获取用户信息也将从此类中快捷的获取,
 * Created by Administrator on 2018/1/26.
 */

public class YUE_UserInfoManager {
    private Context mContext;


    private static YUE_UserInfoManager mInstance;

    private YUE_UserInfoManager(Context context) {
        super();
        this.mContext = context;
    }

    public static YUE_UserInfoManager getInstance() {
        return mInstance;
    }

    public static void init(Context context) {
        mInstance = new YUE_UserInfoManager(context);
    }

    /**
     * 当新用户登录时,将清除本地好友列表数据库数据,再从服务端同步数据更新到本地好友列表数据库
     */
    public void upDateContacts() {
        //登录则清空好友列表数据库,从服务端获取,并更新到本地数据库,
        DBManager.getInstance().getSession().getFriendDao().deleteAll();
        ApiServicesManager.getInstence().getYueapi().getFriendsList((String)
                YUE_SharedPreferencesUtils
                        .getParam(mContext, YUE_SPsave.YUE_LOGING_PHONE, ""), "1")
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())//这里没有操作ui直接更新数据库,那就在io线程继续操作数据库,不转ui线程
                .subscribe(new Observer<YUE_FriendsListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(YUE_FriendsListBean yue_friendsListBean) {
                        List<YUE_FriendsBean> list = yue_friendsListBean.getValue();
                        //存数据库
                        insertIntoSql(list);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void insertIntoSql(List<YUE_FriendsBean> list) {
        DBManager.getInstance().getSession().getFriendDao().deleteAll();
        for (YUE_FriendsBean yueFriendsBean : list
                ) {
            Friend friend = new Friend(null, yueFriendsBean.getNickname(), null,
                    null, null, null,
                    null, null, yueFriendsBean.getPhonenum());
            DBManager.getInstance().getSession().getFriendDao().insert(friend);
        }

        YUE_LogUtils.d("insert--YUE_FriendsBean", list.size() + "insert Success!");
        List<Friend> l = DBManager.getInstance().getSession().getFriendDao().loadAll();
        for (Friend friend : l
                ) {
            YUE_LogUtils.d("结果:-->", friend.getNickName() + "," + friend.getPhoneNumber());
        }
    }

    /**
     * 从本地数据库读取好友列表,异步操作
     *
     * @return 好友列表信息集合
     */

    public void getFriendList(final ICommonInteractorCallback l) {
        Observable.create(new ObservableOnSubscribe<List<Friend>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Friend>> e) throws Exception {
                List<Friend> list = DBManager.getInstance().getSession().getFriendDao().loadAll();
                e.onNext(list);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Friend>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        l.addDisaposed(d);
                    }

                    @Override
                    public void onNext(List<Friend> friends) {
                        l.loadSuccess(friends);
                    }

                    @Override
                    public void onError(Throwable e) {
                        l.loadFailed();
                    }

                    @Override
                    public void onComplete() {
                        l.loadCompleted();
                    }
                });
    }

}
