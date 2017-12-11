package com.yuecheng.yue.ui.activity;

import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by yuecheng on 2017/10/29.
 */

public interface YUE_ILoginView {

    View getViewParent();//需要父控件,popwindow展示在父控件的相对位置.

    void btnRandLVisable();//注册和登录按钮可见

    void btnRandLInVisable();//注册和登录按钮不可见

    void jumpToActivity(Class c);//跳转到其他活动

    Handler getHandler();//交互更新UI

    void finishActivity();//跳转至主页结束掉自己

    void showMessage(String s);//弹出提示
}
