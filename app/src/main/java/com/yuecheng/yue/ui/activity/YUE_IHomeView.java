package com.yuecheng.yue.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;

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
