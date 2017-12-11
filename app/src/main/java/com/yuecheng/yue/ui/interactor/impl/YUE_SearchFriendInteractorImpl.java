package com.yuecheng.yue.ui.interactor.impl;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.yuecheng.yue.R;
import com.yuecheng.yue.http.pinyin.CharacterParser;
import com.yuecheng.yue.ui.bean.YUE_ContactsInfo;
import com.yuecheng.yue.ui.interactor.YUE_ISearchFriendInteractor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by administraot on 2017/12/8.
 */

public class YUE_SearchFriendInteractorImpl implements YUE_ISearchFriendInteractor {


    @Override
    public List<YUE_ContactsInfo> obtionContacts(Context context) {
        List<YUE_ContactsInfo> list = new ArrayList<>();
        try {
            Uri contactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            //获取手机通讯录联系人
            ContentResolver resolver = context.getContentResolver();
            Cursor cursor = resolver.query(contactUri,
                    new String[]{"display_name", "sort_key", "contact_id", "data1", "photo_id"},
                    null, null, "sort_key");
            String contactName;
            String contactNumber;
            String contactSortKey;
            int contactId;
            while (cursor.moveToNext()) {
                //得到联系人名称
                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract
                        .CommonDataKinds.Phone.DISPLAY_NAME));
                //得到手机号码
                contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract
                        .CommonDataKinds.Phone.NUMBER));
                //得到联系人ID
                contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds
                        .Phone.CONTACT_ID));
                contactSortKey = getSortkey(cursor.getString(1));
                //得到联系人头像ID
                Long photoid = cursor.getLong(cursor.getColumnIndex(ContactsContract
                        .CommonDataKinds.Phone.PHOTO_ID));
                //得到联系人头像Bitamp
                Bitmap contactPhoto = null;
                if (photoid > 0) {
                    Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,
                            contactId);
                    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream
                            (resolver, uri);
                    contactPhoto = BitmapFactory.decodeStream(input);
                } else {
                    contactPhoto = BitmapFactory.decodeResource(context.getResources(), R
                            .drawable.haha);
                }
                YUE_ContactsInfo contactsInfo = new YUE_ContactsInfo(contactName, contactNumber,
                        contactSortKey, contactId, contactPhoto);
                if (contactName != null)
                    list.add(contactsInfo);
            }
            cursor.close();//使用完后一定要将cursor关闭，不然会造成内存泄露等问题

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            context = null;
        }
        return list;
    }

    private static String getSortkey(String sortKeyString) {
        String key = sortKeyString.substring(0, 1).toUpperCase();
        if (key.matches("[A-Z]")) {
            return key;
        } else
            return "#";   //获取sort key的首个字符，如果是英文字母就直接返回，否则返回#。
    }

    @Override
    public List<YUE_ContactsInfo> filter(List<YUE_ContactsInfo> contactsInfos, String newText) {
        String lowerCaseQuery = newText.toLowerCase();
        List<YUE_ContactsInfo> filterList = new ArrayList<>();
        for (YUE_ContactsInfo c : contactsInfos) {
            String nameEn = CharacterParser.getInstance().getSpelling(c.getName()).toLowerCase();
            String numberEn = c.getNumber().toLowerCase();
            String name = c.getName();
            String number = c.getNumber();
            if (name.contains(lowerCaseQuery) || number.contains(lowerCaseQuery) || nameEn
                    .contains(lowerCaseQuery) || numberEn.contains(lowerCaseQuery)) {
                filterList.add(c);
            }
        }
        return filterList;
    }
}
