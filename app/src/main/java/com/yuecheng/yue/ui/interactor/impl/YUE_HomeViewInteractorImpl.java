package com.yuecheng.yue.ui.interactor.impl;


import android.net.Uri;
import android.support.v4.app.Fragment;
import com.yuecheng.yue.R;
import com.yuecheng.yue.ui.bean.YUE_SPsave;
import com.yuecheng.yue.ui.fragment.impl.YUE_ContactsFragment;
import com.yuecheng.yue.ui.fragment.impl.YUE_ConversationListFragment;
import com.yuecheng.yue.ui.fragment.impl.YUE_MineFragment;
import com.yuecheng.yue.ui.fragment.impl.YUE_MomentsFragment;
import com.yuecheng.yue.ui.interactor.YUE_IHomeViewInteractor;
import com.yuecheng.yue.util.YUE_AppUtils;
import com.yuecheng.yue.util.YUE_SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.rong.imkit.RongContext;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by yuecheng on 2017/11/3.
 */

public class YUE_HomeViewInteractorImpl implements YUE_IHomeViewInteractor {

    @Override
    public List<Map<String, Object>> getdata() {
        List<Map<String, Object>>  data = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for(int i=0;i<8;i++)
        {
            map = new HashMap<String, Object>();
            map.put("img", R.drawable.haha);
            map.put("title", "item"+i);
            data.add(map);
        }
        return data;
    }

    @Override
    public List<Fragment> gerFragmentsList() {
        List<Fragment> fragments = new ArrayList<Fragment>() {
            {
                add(new YUE_ConversationListFragment());
                add(new YUE_ContactsFragment());
                add(new YUE_MomentsFragment());
                add(new YUE_MineFragment());
            }
        };
        return fragments;
    }

}
