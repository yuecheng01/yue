package com.yuecheng.yue.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * 作者：yuecheng on 2017/11/1 13:34
 * 邮箱：1062258956@qq.com
 */

public class YUE_CustomPopWindow {
    private Context mContext;
    private PopupWindow mPopupWindow;
    private int mWidth;
    private int mHeight;
    private Drawable mBackgroundColor;
    private boolean mIsFocusable = true;
    private boolean mIsOutside = true;
    private LayoutInflater mLayoutInflater;
    private int mResLayoutId;
    private View mContentView;
    private int mAnimationStyle;
    private boolean mClippEnable;
    private boolean mIgnoreCheekPress;
    private int mInputMode;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private int mSoftInputMode;
    private boolean mTouchable;
    private View.OnTouchListener mOnTouchListener;

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public YUE_CustomPopWindow(Context context) {
       this.mContext=context;
    }
    public YUE_CustomPopWindow showAsDropDown(View anchor){
        if (null!= mPopupWindow)
            mPopupWindow.showAsDropDown(anchor);
        return this;
    }
    public YUE_CustomPopWindow showAsDropDown(View anchor,int xoff, int yoff){
        if (null != mPopupWindow)
            mPopupWindow.showAsDropDown(anchor,xoff,yoff);
        return this;
    }
    public YUE_CustomPopWindow showAtLocation(View parent,int gravity , int x, int y){
        if (null != mPopupWindow)
            mPopupWindow.showAtLocation(parent,gravity,x,y);
        return this;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public YUE_CustomPopWindow showAsDropDown(View anchor, int xoff, int yoff, int gravity){
        if (null != mPopupWindow)
            mPopupWindow.showAsDropDown(anchor,xoff,yoff,gravity);
        return this;
    }
    private PopupWindow build() {
        if (null == mContentView){
            mContentView = LayoutInflater.from(mContext).inflate(mResLayoutId,null);
        }
        if(mWidth != 0 && mHeight != 0 ){
            mPopupWindow = new PopupWindow(mContentView,mWidth,mHeight);
        }else{
            mPopupWindow = new PopupWindow(mContentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        if(mAnimationStyle != -1){
            mPopupWindow.setAnimationStyle(mAnimationStyle);
        }
        apply(mPopupWindow);//设置一些属性
        mPopupWindow.setFocusable(mIsFocusable);
//        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setBackgroundDrawable(mBackgroundColor);
        mPopupWindow.setOutsideTouchable(mIsOutside);

        if(mWidth == 0 || mHeight == 0){
            mPopupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            //如果外面没有设置宽高的情况下，计算宽高并赋值
            mWidth = mPopupWindow.getContentView().getMeasuredWidth();
            mHeight = mPopupWindow.getContentView().getMeasuredHeight();
        }

        mPopupWindow.update();

        return mPopupWindow;
    }
    /**
     * 关闭popWindow
     */
    public void dissmiss(){
        if(mPopupWindow!=null){
            mPopupWindow.dismiss();
        }
    }
    private void apply(PopupWindow popupWindow) {
        popupWindow.setClippingEnabled(mClippEnable);
        if(mIgnoreCheekPress){
            popupWindow.setIgnoreCheekPress();
        }
        if(mInputMode!=-1){
            popupWindow.setInputMethodMode(mInputMode);
        }
        if(mSoftInputMode!=-1){
            popupWindow.setSoftInputMode(mSoftInputMode);
        }
        if(mOnDismissListener!=null){
            popupWindow.setOnDismissListener(mOnDismissListener);
        }
        if(mOnTouchListener!=null){
            popupWindow.setTouchInterceptor(mOnTouchListener);
        }
        popupWindow.setTouchable(mTouchable);
    }

    public static class PopWindowBuilder{
        private YUE_CustomPopWindow mYUE_customPopWindow;

        public PopWindowBuilder(Context context) {
            mYUE_customPopWindow = new YUE_CustomPopWindow(context);
        }
        public PopWindowBuilder size(int width , int height){
            mYUE_customPopWindow.mWidth = width;
            mYUE_customPopWindow.mHeight = height;
            return this;
        }
        public PopWindowBuilder setFocusable(boolean focusable){
            mYUE_customPopWindow.mIsFocusable = focusable;
            return this;
        }
        public PopWindowBuilder setView(int resLayoutId){
            mYUE_customPopWindow.mResLayoutId = resLayoutId;
            mYUE_customPopWindow.mContentView = null;
            return this;
        }
        public PopWindowBuilder setView(View view){
            mYUE_customPopWindow.mContentView = view;
            mYUE_customPopWindow.mResLayoutId = -1;
            return this;
        }
        public PopWindowBuilder setBackGround(Drawable color){
            mYUE_customPopWindow.mBackgroundColor = color;
            return this;
        }
        public PopWindowBuilder setOutsideTouchable(boolean outsideTouchable){
            mYUE_customPopWindow.mIsOutside = outsideTouchable;
            return this;
        }
        public PopWindowBuilder setAnimationStyle(int animationStyle){
            mYUE_customPopWindow.mAnimationStyle = animationStyle;
            return this;
        }
        public PopWindowBuilder setClippingEnable(boolean enable){
            mYUE_customPopWindow.mClippEnable =enable;
            return this;
        }


        public PopWindowBuilder setIgnoreCheekPress(boolean ignoreCheekPress){
            mYUE_customPopWindow.mIgnoreCheekPress = ignoreCheekPress;
            return this;
        }

        public PopWindowBuilder setInputMethodMode(int mode){
            mYUE_customPopWindow.mInputMode = mode;
            return this;
        }
        public PopWindowBuilder setOnDissmissListener(PopupWindow.OnDismissListener onDissmissListener){
            mYUE_customPopWindow.mOnDismissListener = onDissmissListener;
            return this;
        }


        public PopWindowBuilder setSoftInputMode(int softInputMode){
            mYUE_customPopWindow.mSoftInputMode = softInputMode;
            return this;
        }


        public PopWindowBuilder setTouchable(boolean touchable){
            mYUE_customPopWindow.mTouchable = touchable;
            return this;
        }

        public PopWindowBuilder setTouchIntercepter(View.OnTouchListener touchIntercepter){
            mYUE_customPopWindow.mOnTouchListener = touchIntercepter;
            return this;
        }


        public YUE_CustomPopWindow create(){
            //构建PopWindow
            mYUE_customPopWindow.build();
            return mYUE_customPopWindow;
        }

    }

    
}
