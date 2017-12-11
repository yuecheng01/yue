package com.yuecheng.yue.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.RadioButton;

import com.yuecheng.yue.ui.activity.impl.YUE_LoginActivity;
import com.yuecheng.yue.widget.YUE_DampListView;

import java.util.List;

/**
 * Created by yuecheng on 2017/10/29.
 */

public interface YUE_IHomeView {

    void loadFragments(List<Fragment> mlist, int i);

    void jump2Activity(Class yue_loginActivityClass);

    void showMessage(String s);

    Intent getIntent1();

    void jumpToActivity(Intent themeintent);

}
