package com.yuecheng.yue.ui.activity;

import android.net.Uri;
import android.view.View;

/**
 * Created by administraot on 2017/11/22.
 */

public interface YUE_ISetUserInfoView {
    void setSex(String mSexName);

    View getWrappedView();

    void setUserSign(String s);//设置签名

    String getUserSign();//获取签名

    void setBirthday(String dateDesc);//设置生日

    void setNowAddress(String addressSelect);//设置现居地址

    void setHomeTownAddress(String homeAdressSelect);//设置家乡地址

    void setIcon(Uri uri);//设置头像
}
