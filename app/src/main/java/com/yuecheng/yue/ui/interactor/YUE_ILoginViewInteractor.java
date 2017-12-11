package com.yuecheng.yue.ui.interactor;

import android.os.Handler;

import com.yuecheng.yue.ui.bean.YUE_LoginBean;

/**
 * Created by yuecheng on 2017/11/5.
 */

public interface YUE_ILoginViewInteractor {
    void getLoginRequestData(String url,
                 String key1, String key2,
                 String param1, String param2,
                 int what,String tag, Handler h);

    void saveNandP(String phoneString, String passwordString);

    void saveRongToken(String token);
}
