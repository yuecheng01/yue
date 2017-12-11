package com.yuecheng.yue.ui.bean;

import android.graphics.Bitmap;

/**
 * Created by administraot on 2017/12/8.
 */

public class YUE_ContactsInfo {
    private String name;
    private String number;
    private String sortKey;
    private int id;
    private Bitmap contactPhoto;


    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getSortKey() {
        return sortKey;
    }

    public int getId() {
        return id;
    }

    public Bitmap getContactPhoto() {
        return contactPhoto;
    }

    public YUE_ContactsInfo(String name, String number, String sortKey, int id, Bitmap contactPhoto) {
        setName(name);
        setNumber(number);
        setSortKey(sortKey);
        setId(id);
        setContactPhoto(contactPhoto);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContactPhoto(Bitmap contactPhoto) {
        this.contactPhoto = contactPhoto;
    }
}
