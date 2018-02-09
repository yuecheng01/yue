package com.yuecheng.yue.widget.selector;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import com.yuecheng.yue.R;
import com.yuecheng.yue.util.CommonUtils;
import com.yuecheng.yue.util.YUE_AppUtils;


/**
 * Created by administraot on 2017/11/24.
 */

public class YUE_BackResUtils {
    private Context mContext;

    private YUE_BackResUtils(Context context) {
        this.mContext = context;
    }

    private static YUE_BackResUtils mInstance = null;

    public static YUE_BackResUtils getInstance(Context context) {
        return mInstance == null ? new YUE_BackResUtils(context) : mInstance;
    }


    //    对应drawable 中的selector
    public StateListDrawable getStateListDrawable() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{-android.R.attr.state_pressed}, getGradientDrawable
                (false));
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, getGradientDrawable
                (true));
        return stateListDrawable;
    }
    //    对应drawable 中的 shape
    private GradientDrawable getGradientDrawable(boolean isPressed) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
//        背景颜色
        if (isPressed) {

            gradientDrawable.setColor(Color.parseColor("#00000000"));
            gradientDrawable.setCornerRadius(10);
        } else {
                        gradientDrawable.setColor(CommonUtils.getColorByAttrId(mContext,R.attr.colorPrimary));
//            gradientDrawable.setColor(Color.parseColor("#1A8EA8"));
            gradientDrawable.setCornerRadius(10);
        }
        gradientDrawable.setBounds(0, 0, CommonUtils.dip2px(mContext, 96),
                CommonUtils.dip2px(mContext, 48));
//        gradientDrawable.setBounds(0, 0, 0, 0);
        return gradientDrawable;
    }
// 如下是绘制登录按钮的 background.主要是解决5.0一下再shap中不能引用attr适配
    public Drawable getLoginDrawableSelector() {
        StateListDrawable stateDrawable = new StateListDrawable();
        GradientDrawable normalDrawable = new GradientDrawable();
        GradientDrawable pressedDrawable = new GradientDrawable();
        GradientDrawable disabledDrawable = new GradientDrawable();

        int[][] states = new int[4][];
        states[0] = new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed};
//        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[3] = new int[]{-android.R.attr.state_enabled}; // disabled state
        states[2] = new int[]{android.R.attr.state_enabled};

        // 为各种状态下的 drawable 设置 attr 颜色值
//        pressedDrawable.setColor(Color.parseColor("#00000000"));
        normalDrawable.setColor(CommonUtils.getColorByAttrId(mContext, R.attr.colorPrimary));
        disabledDrawable.setColor(CommonUtils.getColorByAttrId(mContext, R.attr.colorPrimary));

        // 为各种状态下的 drawable 设置圆角等属性. 仅举一例, 不详述.
        normalDrawable.setCornerRadius(10);
        pressedDrawable.setCornerRadius(10);
        pressedDrawable.setStroke(2,mContext.getResources().getColor(R.color.H333333));
        disabledDrawable.setCornerRadius(10);

        stateDrawable.addState(states[0], pressedDrawable);
//        stateDrawable.addState(states[1], pressedDrawable);
        stateDrawable.addState(states[3], disabledDrawable);
        stateDrawable.addState(states[2], normalDrawable);

        return stateDrawable;
    }
    // 如下是绘制登录按钮的 background.主要是解决5.0一下再shap中不能引用attr适配
    public Drawable getRegDrawableSelector() {
        StateListDrawable stateDrawable = new StateListDrawable();
        GradientDrawable normalDrawable = new GradientDrawable();
        GradientDrawable pressedDrawable = new GradientDrawable();
        GradientDrawable disabledDrawable = new GradientDrawable();

        int[][] states = new int[4][];
        states[0] = new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed};
//        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[3] = new int[]{-android.R.attr.state_enabled}; // disabled state
        states[2] = new int[]{android.R.attr.state_enabled};

        // 为各种状态下的 drawable 设置 attr 颜色值
//        pressedDrawable.setColor(Color.parseColor("#00000000"));
        normalDrawable.setStroke(2,mContext.getResources().getColor(R.color.H333333));
        disabledDrawable.setStroke(2,mContext.getResources().getColor(R.color.H333333));

        // 为各种状态下的 drawable 设置圆角等属性. 仅举一例, 不详述.
        normalDrawable.setCornerRadius(10);
        pressedDrawable.setCornerRadius(10);
        pressedDrawable.setColor(CommonUtils.getColorByAttrId(mContext, R.attr.colorPrimary));
        disabledDrawable.setCornerRadius(10);

        stateDrawable.addState(states[0], pressedDrawable);
//        stateDrawable.addState(states[1], pressedDrawable);
        stateDrawable.addState(states[3], disabledDrawable);
        stateDrawable.addState(states[2], normalDrawable);

        return stateDrawable;
    }

    public Drawable getDividerItemDrawable(){
        GradientDrawable drawable =new GradientDrawable();
        drawable.setSize(CommonUtils.getScreenWidth(mContext),1);
        drawable.setColor(CommonUtils.getColorByAttrId(mContext,R.attr.colorPrimary));
        return drawable;
    }
    public ColorStateList  getTextColorDrawable(){
        int[][] states = new int[2][];
        states[0] =  new int[]{android.R.attr.state_checked};
        states[1] = new int[]{};
        int[] colors = new int[]{CommonUtils.getColorByAttrId(mContext, R.attr.colorPrimary),
                mContext.getResources().getColor(R.color.F888888)};
        ColorStateList stateDrawable = new ColorStateList(states,colors);
        return stateDrawable;
    }



}
