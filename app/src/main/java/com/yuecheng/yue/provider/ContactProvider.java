package com.yuecheng.yue.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yuecheng.yue.db.DBManager;
import com.yuecheng.yue.db.FriendDao;
import com.yuecheng.yue.db.entity.Friend;
import com.yuecheng.yue.util.YUE_LogUtils;

import java.util.List;


/**
 * $desc 存储联系人信息 便于即时更新
 * Created by Administrator on 2017/12/31.
 */

public class ContactProvider extends ContentProvider {
    private String Tag = this.getClass().getSimpleName();
    //主机地址的常量->当前类的完整路径,
    public static final String AUTHORITIES = ContactProvider.class.getCanonicalName();
    //地址匹配对象的创建
    static UriMatcher mUriMatcher;
    private static final int CONTACT = 1;
    //对应联系人表的一个URI常量
    public static Uri URI_CONTACT = Uri.parse("content://" + AUTHORITIES + "/" + FriendDao.TABLENAME);


    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //添加一个匹配规则
        //content://com.yuecheng.yue.provider.ContactProvider/FRIEND  ---->URI_CONTACT
        mUriMatcher.addURI(AUTHORITIES, "/" + FriendDao.TABLENAME, CONTACT);
    }

    @Override
    public boolean onCreate() {
//        mFriendDao = DBManager.getInstance().getSession().getFriendDao();
//        if (mFriendDao != null)
//            return true;
        return false;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    public Friend getFriend(Friend friend, ContentValues contentValues) {
        /*
        * Long id;
        String nickName;
        String portraitUri;
        String displayName;
        String timeStamp;
        String nickNameSpelling;
        String displayNameSpelling;
        String letters;
        * */
        friend.setNickName(contentValues.getAsString("nickName"));
        friend.setPortraitUri(contentValues.getAsString("portraitUri"));
        friend.setDisplayName(contentValues.getAsString("displayName"));
        friend.setTimeStamp(contentValues.getAsString("timeStamp"));
        friend.setNickNameSpelling(contentValues.getAsString("nickNameSpelling"));
        friend.setDisplayNameSpelling(contentValues.getAsString("displayNameSpelling"));
        friend.setLetters(contentValues.getAsString("letters"));
        return friend;
    }

    /*-----------------------------CRUD begin-------------------------------------------------------*/
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        int code = mUriMatcher.match(uri);
        switch (code) {
            case CONTACT:
                //新插入的id
                long id = DBManager.getInstance().getSession().getFriendDao().insert(getFriend
                        (new Friend(), contentValues));
                if (id != -1)
                    YUE_LogUtils.d(Tag, "insert success !");
                //拼接最新的uri --->content://com.yuecheng.yue.provider// .ContactProvider/friend/id
                ContentUris.withAppendedId(uri, id);
                break;
            default:
                break;
        }
        return uri;
    }

    public List<Friend> queryFriendByPhoneNum(String str) {
        return DBManager.getInstance().getSession().getFriendDao().queryBuilder().where
                (FriendDao.Properties.PhoneNumber.eq(str)).build().list();
    }

    /**
     * 删除 号码为给定值的条目
     *
     * @param uri
     * @param s       条件
     * @param strings 具体参数
     * @return
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int code = mUriMatcher.match(uri);
        int deleteCount = 0;
        switch (code) {
            case CONTACT:
                List<Friend> friendList = queryFriendByPhoneNum(s);
                for (Friend f : friendList
                        ) {
                    DBManager.getInstance().getSession().getFriendDao().delete(f);
                }
//                //影响的行数
                deleteCount = friendList.size();
                if (deleteCount > 0)
                    YUE_LogUtils.d(Tag, "delete success !" + deleteCount);
                break;
            default:
                break;
        }
        return deleteCount > 0 ? deleteCount : 0;
    }

    /**
     * @param uri
     * @param contentValues
     * @param s
     * @param strings
     * @return
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int code = mUriMatcher.match(uri);
        int updateCount = 0;
        switch (code) {
            case CONTACT:
                List<Friend> friendList = queryFriendByPhoneNum(s);
                for (Friend f : friendList
                        ) {
                    Friend friend = getFriend(f, contentValues);
                    DBManager.getInstance().getSession().getFriendDao().update(friend);
                }

                updateCount = friendList.size();
                if (updateCount > 0)
                    YUE_LogUtils.d(Tag, "update success" + updateCount);
                break;
            default:
                break;
        }
        return updateCount > 0 ? updateCount : 0;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int code = mUriMatcher.match(uri);
        Cursor cursor = null;
        switch (code) {
            case CONTACT:
                cursor = DBManager.getInstance().getSession().getFriendDao().queryBuilder()
                        .buildCursor().query();
                YUE_LogUtils.d(Tag, "query success !");
                break;
            default:
                break;
        }
        return cursor;
    }
    /*-----------------------------CRUD end-------------------------------------------------------*/

}
