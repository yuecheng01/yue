package com.yuecheng.yue.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yuecheng.yue.R;
import com.yuecheng.yue.ui.bean.YUE_SPsave;
import com.yuecheng.yue.util.ColorUtil;
import com.yuecheng.yue.util.CommonUtils;
import com.yuecheng.yue.util.StatusBarCompat;
import com.yuecheng.yue.util.YUE_SharedPreferencesUtils;
import com.yuecheng.yue.util.YUE_ToastUtils;


/**
 * Created by yuecheng on 2017/10/29.
 */

public abstract class YUE_BaseActivity extends AppCompatActivity {
    /**
     * Screen information
     */
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;
    protected boolean statusBarCompat = true;

    /**
     * context
     */
    protected Context mContext = null;

    @SuppressWarnings("unchecked")
    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        overridePendingTransition(R.anim.right_in, R.anim.right_out);
        super.onCreate(savedInstanceState);
        mContext = this;
        String mThem = (String) YUE_SharedPreferencesUtils.getParam(this, YUE_SPsave.YUE_THEM,
                "默认主题");

        Window window = getWindow();
        switch (mThem) {
            case "默认主题":
                setTheme(R.style.AppThemeDefault);
                break;
            case "热情似火":
                setTheme(R.style.AppThemeRed);
                break;
            case "梦幻紫":
                setTheme(R.style.AppThemePurple);
                break;
            case "乌金黑":
                setTheme(R.style.AppThemeHardwareblack);
                break;
            default:
                if (android.os.Build.VERSION.SDK_INT >= 21)
                    window.setStatusBarColor(CommonUtils.getColorByAttrId(mContext,R.attr.colorPrimary));
                break;
        }
        YUE_BaseAppManager.getInstance().addActivity(this);
        /*
        *获取屏幕尺寸
        * */
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;

        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout " +
                    "resource Id");
        }
        if (statusBarCompat) {
            StatusBarCompat.compat(this, CommonUtils.getColorByAttrId(mContext,R.attr.colorPrimary));
            transparent19and20();
        }

        initViewsAndEvents();

    }

    protected void transparent19and20() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /*
    *功能操作
    * */
    protected abstract void initViewsAndEvents();

    /*
    *加载的布局
    * */
    protected abstract int getContentViewLayoutID();

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void finish() {
        super.finish();
        YUE_BaseAppManager.getInstance().removeActivity(this);
//        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    protected void startActivity(Class a) {
        Intent intent = new Intent();
        intent.setClass(mContext, a);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void ShowMessage(String s) {
        YUE_ToastUtils.getInstance(this).showmessage(s);
    }

    protected void showSnackBar(View v,String s){
        Snackbar snackBar =Snackbar.make(v,s,Snackbar.LENGTH_SHORT);
        //设置SnackBar背景颜色
        snackBar.getView().setBackgroundColor(CommonUtils.getColorByAttrId(mContext,R.attr.colorPrimary));
        //设置按钮文字颜色
        snackBar.setActionTextColor(Color.WHITE);
        snackBar.show();
    }
}
