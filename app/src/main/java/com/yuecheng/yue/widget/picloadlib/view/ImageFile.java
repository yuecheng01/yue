package com.yuecheng.yue.widget.picloadlib.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.GridView;

import com.yuecheng.yue.R;
import com.yuecheng.yue.ui.bean.YUE_SPsave;
import com.yuecheng.yue.util.CommonUtils;
import com.yuecheng.yue.util.YUE_SharedPreferencesUtils;
import com.yuecheng.yue.widget.picloadlib.PhotoPickActivity;
import com.yuecheng.yue.widget.picloadlib.adapter.FolderAdapter;
import com.yuecheng.yue.widget.picloadlib.util.Bimp;
import com.yuecheng.yue.widget.picloadlib.util.PublicWay;

public class ImageFile extends AppCompatActivity {
    private FolderAdapter mFolderAdapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   此部分主要是适配我的主题切换，这个库我没有集成项目的baseactivity，所以此处重复添加此代码了。
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
//   此部分主要是适配我的主题切换，这个库我没有集成项目的baseactivity，所以此处重复添加此代码了。
        setContentView(R.layout.activity_imagefile_layout);
        PublicWay.activityList.add(this);
        mContext = this;
        initToolBar();
        GridView mGridView = (GridView) findViewById(R.id.fileGridView);
        mFolderAdapter = new FolderAdapter(this);
        mGridView.setAdapter(mFolderAdapter);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.setClass(mContext, PhotoPickActivity.class);
            startActivity(intent);
        }

        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cancle, menu);
        return true;
    }
    private void initToolBar() {
        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();

        //使能app bar的导航功能
        ab.setDisplayHomeAsUpEnabled(true);

        ab.setTitle("选择相册");
        mToolBar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolBar.setOnMenuItemClickListener(onMenuItemClick);
    }
    Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_cancle:
                    //todo 取消按钮事件
                    //清空选择的图片
                    Bimp.tempSelectBitmap.clear();
                    Intent intent = new Intent();
                    intent.setClass(ImageFile.this, PhotoPickActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };
}
