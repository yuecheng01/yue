package com.yuecheng.yue.widget.picloadlib.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.yuecheng.yue.R;
import com.yuecheng.yue.ui.bean.YUE_SPsave;
import com.yuecheng.yue.util.CommonUtils;
import com.yuecheng.yue.util.YUE_SharedPreferencesUtils;
import com.yuecheng.yue.widget.picloadlib.PhotoPickActivity;
import com.yuecheng.yue.widget.picloadlib.adapter.AlbumGridViewAdapter;
import com.yuecheng.yue.widget.picloadlib.util.Bimp;
import com.yuecheng.yue.widget.picloadlib.util.ImageItem;
import com.yuecheng.yue.widget.picloadlib.util.PublicWay;
import com.yuecheng.yue.widget.selector.YUE_BackResUtils;

import java.util.ArrayList;

/**
 * 这个是显示一个文件夹里面的所有图片时的界面
 *
 * @author king
 * @version 2014年10月18日  下午11:49:10
 * @QQ:595163260
 */
public class ShowAllPhoto extends AppCompatActivity {
    private GridView mGridView;
    private ProgressBar mProgressBar;
    private AlbumGridViewAdapter gridImageAdapter;
    // 完成按钮
    private Button mOkButton;
    // 预览按钮
    private Button mPreview;
    private Intent intent;
    private Context mContext;
    public static ArrayList<ImageItem> dataList = new ArrayList<ImageItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        setContentView(R.layout.plugin_camera_show_all_photo);
        mContext = this;
        initToolBar();
        mPreview = (Button) findViewById(R.id.showallphoto_preview);
        mOkButton = (Button) findViewById(R.id.showallphoto_ok_button);

        mPreview.setBackground(YUE_BackResUtils.getInstance(this).getRegDrawableSelector());
        mOkButton.setBackground(YUE_BackResUtils.getInstance(this).getLoginDrawableSelector());

        mPreview.setOnClickListener(new PreviewListener());
        init();
        initListener();
        isShowOkBt();
    }

    private void initToolBar() {
        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        ActionBar ab= getSupportActionBar();

        //使能app bar的导航功能
        ab.setDisplayHomeAsUpEnabled(true);
        this.intent = getIntent();
        String folderName = intent.getStringExtra("folderName");
        if (folderName.length() > 8) {
            folderName = folderName.substring(0, 9) + "...";
        }
        ab.setTitle(folderName);
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
                    intent.setClass(mContext, PhotoPickActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub  
            gridImageAdapter.notifyDataSetChanged();
        }
    };

    private class PreviewListener implements OnClickListener {
        public void onClick(View v) {
            if (Bimp.tempSelectBitmap.size() > 0) {
                intent.putExtra("position", "2");
                intent.setClass(ShowAllPhoto.this, GalleryActivity.class);
                startActivity(intent);
            }
        }
    }

    private void init() {
        IntentFilter filter = new IntentFilter("data.broadcast.action");
        registerReceiver(broadcastReceiver, filter);
        mProgressBar = (ProgressBar) findViewById(R.id.showallphoto_progressbar);
        mProgressBar.setVisibility(View.GONE);
        mGridView = (GridView) findViewById(R.id.showallphoto_myGrid);
        gridImageAdapter = new AlbumGridViewAdapter(this, dataList,
                Bimp.tempSelectBitmap);
        mGridView.setAdapter(gridImageAdapter);
        mOkButton = (Button) findViewById(R.id.showallphoto_ok_button);
    }

    private void initListener() {

        gridImageAdapter
                .setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {
                    public void onItemClick(final ToggleButton toggleButton,
                                            int position, boolean isChecked,
                                            Button button) {
                        if (Bimp.tempSelectBitmap.size() >= PublicWay.num && isChecked) {
                            button.setVisibility(View.GONE);
                            toggleButton.setChecked(false);
                            Toast.makeText(ShowAllPhoto.this, "超上限", Toast.LENGTH_SHORT)
                                    .show();
                            return;
                        }

                        if (isChecked) {
                            button.setVisibility(View.VISIBLE);
                            Bimp.tempSelectBitmap.add(dataList.get(position));
                            mOkButton.setText("完成" + "(" + Bimp.tempSelectBitmap.size()
                                    + "/" + PublicWay.num + ")");
                        } else {
                            button.setVisibility(View.GONE);
                            Bimp.tempSelectBitmap.remove(dataList.get(position));
                            mOkButton.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/" +
                                    PublicWay.num + ")");
                        }
                        isShowOkBt();
                    }
                });

        mOkButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mOkButton.setClickable(false);
//				if (PublicWay.photoService != null) {
//					PublicWay.selectedDataList.addAll(Bimp.tempSelectBitmap);
//					Bimp.tempSelectBitmap.clear();
//					PublicWay.photoService.onActivityResult(0, -2,
//							intent);
//				}
                intent.setClass(mContext, PhotoPickActivity.class);
                startActivity(intent);
                // Intent intent = new Intent();
                // Bundle bundle = new Bundle();
                // bundle.putStringArrayList("selectedDataList",
                // selectedDataList);
                // intent.putExtras(bundle);
                // intent.setClass(ShowAllPhoto.this, UploadPhoto.class);
                // startActivity(intent);
                finish();

            }
        });

    }

    public void isShowOkBt() {
        if (Bimp.tempSelectBitmap.size() > 0) {
            mOkButton.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
            mPreview.setPressed(true);
            mOkButton.setPressed(true);
            mPreview.setClickable(true);
            mOkButton.setClickable(true);
            mOkButton.setTextColor(Color.WHITE);
            mPreview.setTextColor(Color.WHITE);
        } else {
            mOkButton.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
            mPreview.setPressed(false);
            mPreview.setClickable(false);
            mOkButton.setPressed(false);
            mOkButton.setClickable(false);
            mOkButton.setTextColor(Color.parseColor("#E1E0DE"));
            mPreview.setTextColor(Color.parseColor("#E1E0DE"));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            intent.setClass(ShowAllPhoto.this, ImageFile.class);
            startActivity(intent);
        }

        return false;

    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        isShowOkBt();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        synchronized (PublicWay.activityList){
            if (PublicWay.activityList.contains(this))
                PublicWay.activityList.remove(this);
        }
    }
}
