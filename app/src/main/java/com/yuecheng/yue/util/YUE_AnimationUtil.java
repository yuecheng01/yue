package com.yuecheng.yue.util;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by yuecheng on 2017/11/1.
 */

public class YUE_AnimationUtil {
    private static final String TAG = YUE_AnimationUtil.class.getSimpleName();

    /**
     * 从控件所在位置下移出父控件
     *
     * @return
     */
    public static TranslateAnimation moveToViewBottom() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_PARENT,
                0.0f, Animation.RELATIVE_TO_PARENT, 1.0f);
        mHiddenAction.setDuration(1000);
        return mHiddenAction;
    }

    /**
     * 从父控件的底部上移到控件所在位置
     *
     * @return
     */
    public static TranslateAnimation moveToViewLocation() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_PARENT,
                1.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
        mHiddenAction.setDuration(1000);
        return mHiddenAction;
    }
}
