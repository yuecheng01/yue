package com.yuecheng.yue.widget.picloadlib.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.yuecheng.yue.R;
import com.yuecheng.yue.ui.bean.YUE_SPsave;
import com.yuecheng.yue.util.CommonUtils;
import com.yuecheng.yue.util.YUE_SharedPreferencesUtils;
import com.yuecheng.yue.widget.photoview.PhotoView;
import com.yuecheng.yue.widget.picloadlib.PhotoPickActivity;
import com.yuecheng.yue.widget.picloadlib.util.Bimp;
import com.yuecheng.yue.widget.picloadlib.util.PublicWay;
import com.yuecheng.yue.widget.picloadlib.zoom.ViewPagerFixed;
import com.yuecheng.yue.widget.selector.YUE_BackResUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administraot on 2017/11/26.
 */

public class GalleryActivity extends AppCompatActivity {
    private Intent intent;

    // 完成按钮
    private Button send_bt;
    //获取前一个activity传过来的position
    private int position;
    //当前的位置
    private int location = 0;

    private ArrayList<View> listViews = null;
    private ViewPagerFixed pager;
    private MyPageAdapter adapter;

    public List<Bitmap> bmp = new ArrayList<Bitmap>();
    public List<String> drr = new ArrayList<String>();
    public List<String> del = new ArrayList<String>();

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PublicWay.activityList.add(this);
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
        setContentView(R.layout.plugin_camera_gallery);
        mContext = this;
        initToolBar();
        initViews();
        initDataEvents();
    }

    private void initToolBar() {
        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();

        //使能app bar的导航功能
        ab.setDisplayHomeAsUpEnabled(true);

        ab.setTitle("相册");
        mToolBar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolBar.setOnMenuItemClickListener(onMenuItemClick);
        //        toolbar 返回按钮监听
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(GalleryActivity.this, AlbumActivity.class);
                startActivity(intent);
            }
        });
    }
    Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_delete:
                    //todo 删除按钮事件
                    if (listViews.size() == 1) {
                        Bimp.tempSelectBitmap.clear();
                        Bimp.max = 0;
                        send_bt.setText("完成"+"(" + Bimp.tempSelectBitmap.size() + "/"+PublicWay.num+")");
                        Intent intent = new Intent("data.broadcast.action");
                        sendBroadcast(intent);
                        finish();
                    } else {
                        Bimp.tempSelectBitmap.remove(location);
                        Bimp.max--;
                        pager.removeAllViews();
                        listViews.remove(location);
                        adapter.setListViews(listViews);
                        send_bt.setText("完成"+"(" + Bimp.tempSelectBitmap.size() + "/"+PublicWay.num+")");
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }
            return true;
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }
    private void initDataEvents() {
        send_bt.setBackground(YUE_BackResUtils.getInstance(this).getLoginDrawableSelector());
        send_bt.setOnClickListener(new GallerySendListener());
        intent = getIntent();
        position = Integer.parseInt(intent.getStringExtra("position"));
        // 为发送按钮设置文字
        isShowOkBt();
        pager.addOnPageChangeListener(pageChangeListener);
        for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
            initListViews( Bimp.tempSelectBitmap.get(i).getBitmap() );
        }
        adapter = new MyPageAdapter(listViews);
        pager.setAdapter(adapter);
        pager.setPageMargin((int)10);
        int id = intent.getIntExtra("ID", 0);
        pager.setCurrentItem(id);
    }

    private void initListViews(Bitmap bm) {
        if (listViews == null)
            listViews = new ArrayList<View>();
        PhotoView img = new PhotoView(this);
        img.enable();
        img.setBackgroundColor(0xff000000);
        img.setImageBitmap(bm);
        img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        listViews.add(img);
    }


    private void initViews() {
        send_bt = (Button) findViewById(R.id.send_button);
        pager = (ViewPagerFixed) findViewById(R.id.gallery01);
    }

    public void isShowOkBt() {
        if (Bimp.tempSelectBitmap.size() > 0) {
            send_bt.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/" +
                    PublicWay.num + ")");
            send_bt.setPressed(true);
            send_bt.setClickable(true);
            send_bt.setTextColor(Color.WHITE);
        } else {
            send_bt.setPressed(false);
            send_bt.setClickable(false);
            send_bt.setTextColor(Color.parseColor("#E1E0DE"));
        }
    }

    // 完成按钮的监听
    private class GallerySendListener implements View.OnClickListener {
        public void onClick(View v) {
            finish();
            intent.setClass(mContext, PhotoPickActivity.class);
            startActivity(intent);
        }
    }
    /**
     * 监听返回按钮
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(position==1){
                this.finish();
                intent.setClass(GalleryActivity.this, AlbumActivity.class);
                startActivity(intent);
            }else if(position==2){
                this.finish();
                intent.setClass(GalleryActivity.this, ShowAllPhoto.class);
                startActivity(intent);
            }
        }
        return true;
    }
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        public void onPageSelected(int arg0) {
            location = arg0;
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private class MyPageAdapter extends PagerAdapter {

        private ArrayList<View> listViews;// content

        private int size;// 页数

        public MyPageAdapter(ArrayList<View> listViews) {// 构造函数
            // 初始化viewpager的时候给的一个页面
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public void setListViews(ArrayList<View> listViews) {// 自己写的一个方法用来添加数据
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public int getCount() {// 返回数量
            return size;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void destroyItem(View arg0, int arg1, Object arg2) {// 销毁view对象
            ((ViewPager) arg0).removeView(listViews.get(arg1 % size));
        }

        public void finishUpdate(View arg0) {
        }

        public Object instantiateItem(View arg0, int arg1) {// 返回view对象
            try {
                ((ViewPager) arg0).addView(listViews.get(arg1 % size), 0);

            } catch (Exception e) {
            }
            return listViews.get(arg1 % size);
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }


    }
}