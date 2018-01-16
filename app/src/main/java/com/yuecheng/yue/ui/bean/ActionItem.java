package com.yuecheng.yue.ui.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2018/1/11.
 */

public class ActionItem {
    // 定义图片对象
    public Drawable mDrawable;
    // 定义文本对象
    public CharSequence mTitle;

    public ActionItem(CharSequence title) {
        mDrawable = null;
        mTitle = title;
    }
}
