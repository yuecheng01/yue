package com.yuecheng.yue.widget.picloadlib.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.yuecheng.yue.R;
import com.yuecheng.yue.ui.bean.YUE_SPsave;
import com.yuecheng.yue.util.CommonUtils;
import com.yuecheng.yue.util.YUE_SharedPreferencesUtils;
import com.yuecheng.yue.widget.picloadlib.PhotoPickActivity;
import com.yuecheng.yue.widget.picloadlib.adapter.AlbumGridViewAdapter;
import com.yuecheng.yue.widget.picloadlib.util.AlbumHelper;
import com.yuecheng.yue.widget.picloadlib.util.Bimp;
import com.yuecheng.yue.widget.picloadlib.util.ImageBucket;
import com.yuecheng.yue.widget.picloadlib.util.ImageItem;
import com.yuecheng.yue.widget.picloadlib.util.PublicWay;
import com.yuecheng.yue.widget.selector.YUE_BackResUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 从图库选择到本图片选择界面
 * Created by administraot on 2017/11/26.
 */

public class AlbumActivity extends AppCompatActivity implements View.OnClickListener {

    //显示手机里的所有图片的列表控件
    private GridView mGridView;
    //当手机里没有图片时，提示用户没有图片的控件
    private TextView mNoPic;
    //gridView的adapter
    private AlbumGridViewAdapter mGridImageAdapter;
    //完成按钮
    private Button mOkButton;
    // 预览按钮
    private Button mPreview;
    private ArrayList<ImageItem> mDataList;
    private AlbumHelper mHelper;
    public static List<ImageBucket> mContentList;
    public static Bitmap mBitmap;
    private Intent mIntent;
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
        setContentView(R.layout.activity_album_layout);
        mContext = this;
        //注册一个广播，这个广播主要是用于在GalleryActivity进行预览时，防止当所有图片都删除完后，再回到该页面时被取消选中的图片仍处于选中状态
        IntentFilter filter = new IntentFilter("picdata.broadcast.action");
        registerReceiver(broadcastReceiver, filter);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable
                .plugin_camera_no_pictures);
        initToolBar();
        initViews();
        initDataEvents();
    }

    private void initViews() {
        mGridView = (GridView) findViewById(R.id.myGrid);
        mNoPic = (TextView) findViewById(R.id.myText);
        mOkButton = (Button) findViewById(R.id.ok_button);
        mPreview = (Button) findViewById(R.id.preview);
    }

    private void initDataEvents() {
        mHelper = AlbumHelper.getHelper();
        mHelper.init(getApplicationContext());

        mContentList = mHelper.getImagesBucketList(false);
        mDataList = new ArrayList<ImageItem>();
        for (int i = 0; i < mContentList.size(); i++) {
            mDataList.addAll(mContentList.get(i).imageList);
        }
        mOkButton.setOnClickListener(this);
        mPreview.setOnClickListener(this);
        mPreview.setBackground(YUE_BackResUtils.getInstance(this).getRegDrawableSelector());
        mIntent = getIntent();
        mGridImageAdapter = new AlbumGridViewAdapter(this, mDataList,
                Bimp.tempSelectBitmap);
        mGridView.setAdapter(mGridImageAdapter);
        mGridView.setEmptyView(mNoPic);
        mOkButton.setBackground(YUE_BackResUtils.getInstance(this).getLoginDrawableSelector());
        mOkButton.setText("完成" + "(" + Bimp.tempSelectBitmap.size()
                + "/" + PublicWay.num + ")");

        mGridImageAdapter
                .setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(final ToggleButton toggleButton,
                                            int position, boolean isChecked, Button chooseBt) {
                        if (Bimp.tempSelectBitmap.size() >= PublicWay.num) {
                            toggleButton.setChecked(false);
                            chooseBt.setVisibility(View.GONE);
                            if (!removeOneData(mDataList.get(position))) {
                                Toast.makeText(AlbumActivity.this, "超出可选图片张数",
                                        Toast.LENGTH_SHORT).show();
                            }
                            return;
                        }
                        if (isChecked) {
                            chooseBt.setVisibility(View.VISIBLE);
                            Bimp.tempSelectBitmap.add(mDataList.get(position));
                            mOkButton.setText("完成" + "(" + Bimp.tempSelectBitmap.size()
                                    + "/" + PublicWay.num + ")");
                        } else {
                            Bimp.tempSelectBitmap.remove(mDataList.get(position));
                            chooseBt.setVisibility(View.GONE);
                            mOkButton.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/" +
                                    PublicWay.num + ")");
                        }
                        isShowOkBt();
                    }
                });

        mOkButton.setOnClickListener(new AlbumSendListener());
    }

    public void isShowOkBt() {
        if (Bimp.tempSelectBitmap.size() > 0) {
            mOkButton.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num +
                    ")");
            mPreview.setPressed(true);
            mOkButton.setPressed(true);
            mPreview.setClickable(true);
            mOkButton.setClickable(true);
            mOkButton.setTextColor(Color.WHITE);
            mPreview.setTextColor(Color.WHITE);
        } else {
            mOkButton.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num +
                    ")");
            mPreview.setPressed(false);
            mPreview.setClickable(false);
            mOkButton.setPressed(false);
            mOkButton.setClickable(false);
            mOkButton.setTextColor(Color.parseColor("#E1E0DE"));
            mPreview.setTextColor(Color.parseColor("#E1E0DE"));
        }
    }

    // 完成按钮的监听
    private class AlbumSendListener implements View.OnClickListener {
        public void onClick(View v) {
            overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
            mIntent.setClass(AlbumActivity.this, PhotoPickActivity.class);
            startActivity(mIntent);
            finish();
        }

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            mGridImageAdapter.notifyDataSetChanged();
        }
    };

    private boolean removeOneData(ImageItem imageItem) {
        if (Bimp.tempSelectBitmap.contains(imageItem)) {
            Bimp.tempSelectBitmap.remove(imageItem);
            mOkButton.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num +
                    ")");
            return true;
        }
        return false;
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
                mIntent.setClass(AlbumActivity.this, ImageFile.class);
                startActivity(mIntent);
            }
        });
    }


    Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_cancle:
                    //todo 取消按钮事件
//                    Bimp.tempSelectBitmap.clear();
                    mIntent.setClass(mContext, PhotoPickActivity.class);
                    startActivity(mIntent);
                    break;
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cancle, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok_button:
                overridePendingTransition(R.anim.activity_translate_in, R.anim
                        .activity_translate_out);
                mIntent.setClass(AlbumActivity.this, PhotoPickActivity.class);
                startActivity(mIntent);
                finish();
                break;
            case R.id.preview:
                if (Bimp.tempSelectBitmap.size() > 0) {
                    mIntent.putExtra("position", "1");
                    mIntent.setClass(AlbumActivity.this, GalleryActivity.class);
                    startActivity(mIntent);
                }
                break;
        }
    }

    //监听返回按钮
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mIntent.setClass(AlbumActivity.this, ImageFile.class);
            startActivity(mIntent);
        }
        return false;

    }

    @Override
    protected void onRestart() {
        isShowOkBt();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
