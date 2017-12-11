package com.yuecheng.yue.ui.activity.impl;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yuecheng.yue.R;
import com.yuecheng.yue.base.YUE_BaseActivity;

import java.util.List;

/**
 * Created by administraot on 2017/12/4.
 */

public class YUE_QRcodeActivity extends YUE_BaseActivity implements View.OnClickListener{

private ImageView mLight;
private TextView mScanLocaPic;
private boolean isLight = false;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_qrcode_layout;
    }

    @Override
    protected void initViewsAndEvents() {
        initToolBar();
        initView();
        initDataEvents();
        /**
         * 执行扫面Fragment的初始化操作
         */
        CaptureFragment captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);

        captureFragment.setAnalyzeCallback(analyzeCallback);
        /**
         * 替换我们的扫描控件
         */ getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();

    }

    private void initDataEvents() {
        mLight.setOnClickListener(this);
        mScanLocaPic.setOnClickListener(this);
    }

    private void initView() {
        mLight = findView(R.id.light);
        mScanLocaPic = findView(R.id.scanlocalpic);
    }

    private void initToolBar() {
        Toolbar mToolBar = findView(R.id.toolbar);
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();

        //使能app bar的导航功能
        ab.setDisplayHomeAsUpEnabled(true);

        ab.setTitle("扫一扫");
        mToolBar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            YUE_QRcodeActivity.this.setResult(RESULT_OK, resultIntent);
            YUE_QRcodeActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            YUE_QRcodeActivity.this.setResult(RESULT_OK, resultIntent);
            YUE_QRcodeActivity.this.finish();
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.light:
                if (!isLight){
                    CodeUtils.isLightEnable(false);
                    isLight = true;
                }else {
                    CodeUtils.isLightEnable(true);
                    isLight = false;
                }
                break;
            case R.id.scanlocalpic:
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                if (!isIntentAvailable(this, intent)) {
                    return;
                }
                startActivityForResult(intent, 1);
                break;
        }
    }
    /**
     * @param intent
     * @return
     */
    protected boolean isIntentAvailable(Activity activity, Intent intent) {
        PackageManager packageManager = activity.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
                Uri uri = data.getData();
                ContentResolver cr = getContentResolver();
                try {
                    Bitmap mBitmap = MediaStore.Images.Media.getBitmap(cr, uri);//显得到bitmap图片

                    CodeUtils.analyzeBitmap(uri.toString(), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            Toast.makeText(YUE_QRcodeActivity.this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(YUE_QRcodeActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                        }
                    });

                    if (mBitmap != null) {
                        mBitmap.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
