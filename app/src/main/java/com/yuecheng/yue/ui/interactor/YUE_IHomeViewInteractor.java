package com.yuecheng.yue.ui.interactor;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.yuecheng.yue.ui.adapter.YUE_LeftMenuAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by yuecheng on 2017/11/3.
 */

public interface YUE_IHomeViewInteractor {
    List<Map<String, Object>> getdata();

    List<Fragment> gerFragmentsList();

    String getToken();
}
