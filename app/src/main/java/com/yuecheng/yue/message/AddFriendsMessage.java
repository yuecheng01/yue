package com.yuecheng.yue.message;

import android.annotation.SuppressLint;
import android.os.Parcel;

import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * Created by Administrator on 2018/1/29.
 */
@SuppressLint("ParcelCreator")
@MessageTag(value = "YUE:AddFriendMsg", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class AddFriendsMessage extends MessageContent {
    @Override
    public byte[] encode() {
        return new byte[0];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
