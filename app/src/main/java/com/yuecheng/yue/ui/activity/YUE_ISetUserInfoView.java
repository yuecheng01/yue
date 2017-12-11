package com.yuecheng.yue.ui.activity;

import android.view.View;

/**
 * Created by administraot on 2017/11/22.
 */

public interface YUE_ISetUserInfoView {
    void setSex(String mSexName);

    View getWrappedView();

    void setUserDesc(String s);

    String getUserDesc();

    void setBirthday(String dateDesc);

    void setAddressSelect(String addressSelect);

    void setHomeAddressSelect(String homeAdressSelect);
}
